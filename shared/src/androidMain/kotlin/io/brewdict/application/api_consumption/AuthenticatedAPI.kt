package io.brewdict.application.api_consumption

import io.brewdict.application.api_consumption.models.LoggedInUser
import io.brewdict.application.api_consumption.models.User
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*

abstract class AuthenticatedAPI(host: String, loginPath: String, registrationPath: String?) : API(host) {
    // TODO: Seperate functionality between authenticated and un-authenticated apis/endpoints.
    private val authenticationEndpoint: AuthenticationEndpoint = AuthenticationEndpoint(this, loginPath, registrationPath)

    var loggedInUser: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = loggedInUser != null

    init{
        loggedInUser = null
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser){
        this.loggedInUser = loggedInUser
    }

    private fun isIdentityEmail(identity: String) : Boolean {
        return identity.contains("@")
    }

    private fun loginSuccessful(user: LoggedInUser) {
        setLoggedInUser(user)

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
                        BearerTokens(
                            accessToken = user.token.accessToken,
                            refreshToken = ""
                        )
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

    fun register(user: User, password: String): Result<LoggedInUser>{
        val result = authenticationEndpoint.register(user, password)

        if (result is Result.Success) {
            loginSuccessful(result.data)
        }

        return result
    }

    fun login(identity: String, password: String): Result<LoggedInUser> {
        val result =
            if (isIdentityEmail(identity)) authenticationEndpoint.emailLogin(identity, password)
            else authenticationEndpoint.usernameLogin(identity, password)

        if (result is Result.Success) {
            loginSuccessful(result.data)
        }

        return result
    }

    fun logout(){
        authenticationEndpoint.logout()

        loggedInUser = null
        client = unauthClient
    }
}