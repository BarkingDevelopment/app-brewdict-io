package io.brewdict.application.api_consumption

import io.brewdict.application.api_consumption.models.LoggedInUser
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*

abstract class AuthenticatedAPI(host: String, loginPath: String) : API(host) {
    // TODO: Seperate functionality between authenticated and un-authenticated apis/endpoints.
    private val authenticationEndpoint: AuthenticationEndpoint = AuthenticationEndpoint(this, loginPath)

    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init{
        user = null
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser){
        this.user = loggedInUser
    }

    private fun isIdentityEmail(identity: String) : Boolean {
        return identity.contains("@")
    }

    fun login(identity: String, password: String): Result<LoggedInUser> {
        val result =
            if (isIdentityEmail(identity)) authenticationEndpoint.emailLogin(identity, password)
            else authenticationEndpoint.usernameLogin(identity, password)



        if (result is Result.Success) {
            setLoggedInUser(result.data)

            client = HttpClient() {
                install(Logging) {
                    level = LogLevel.ALL
                }

                install(HttpTimeout) {
                    requestTimeoutMillis = 15000L
                    connectTimeoutMillis = 15000L
                    socketTimeoutMillis = 15000L
                }

                install(Auth) {
                    bearer {
                        loadTokens {
                            user?.token?.let {
                                BearerTokens(
                                    accessToken = it.accessToken,
                                    refreshToken = ""
                                )
                            }
                        }
                    }
                }

                install(JsonFeature) {
                    serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                        prettyPrint = true
                        ignoreUnknownKeys = true
                        isLenient = true
                        encodeDefaults = false
                    })
                }
            }
        }

        return result
    }

    fun logout(){
        authenticationEndpoint.logout()

        user = null
        client = unauthClient
    }
}