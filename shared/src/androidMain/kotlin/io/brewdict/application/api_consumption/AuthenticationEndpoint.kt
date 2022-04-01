package io.brewdict.application.api_consumption

import io.brewdict.application.api_consumption.models.LoggedInUser
import io.brewdict.application.api_consumption.models.User
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import java.io.IOException

class AuthenticationEndpoint(val api: API, private val loginPath: String, private val registerPath: String?) : Endpoint {
    fun register(user: User, password: String): Result<LoggedInUser> {
        var result: Result<LoggedInUser>

        runBlocking {
            result = try {
                val loggedInUser: LoggedInUser = api.client.post {
                    url(api.host + "/" + registerPath)
                    parameter("username", user.username)
                    parameter("email", user.email)
                    parameter("password", password) //TODO: Hash this before sending.
                    parameter("password_confirmation", password) //TODO: Hash this before sending.
                }

                Result.Success(loggedInUser)
            } catch (e: Throwable) {
                Result.Error(IOException("Error logging in:", e))
            }
        }

        return result
    }

    fun emailLogin(email: String, password: String): Result<LoggedInUser> {
        var result: Result<LoggedInUser>

        runBlocking {
            result = try {
                val loggedInUser: LoggedInUser = api.client.post {
                    url(api.host + "/" + loginPath)
                    parameter("email", email)
                    parameter("password", password) //TODO: Hash this before sending.
                }

                Result.Success(loggedInUser)
            } catch (e: Throwable) {
                Result.Error(IOException("Error logging in:", e))
            }
        }

        return result
    }

    fun usernameLogin(username: String, password: String): Result<LoggedInUser> {
        var result: Result<LoggedInUser>

        runBlocking {
            result = try {
                val loggedInUser: LoggedInUser = api.client.post {
                    url(api.host + "/" + loginPath)
                    parameter("username", username)
                    parameter("password", password) //TODO: Hash this before sending.
                }

                Result.Success(loggedInUser)
            } catch (e: Throwable) {
                Result.Error(IOException("Error logging in.", e))
            }
        }

        return result
    }

    fun logout():  Result<HttpResponse>{
        try {
            runBlocking {
                withTimeout(1000L) {
                    val response: HttpResponse = api.client.post {
                        url("https://api.brewdict.io/logout")
                    }

                    return@withTimeout Result.Success(response)
                }
            }
        }
        catch (e: TimeoutCancellationException) { return Result.Error(e) }

        catch (e: Throwable) { return Result.Error(IOException("Error logging out:", e)) }

        return Result.Error(RuntimeException("Something's gone really wrong here."))
    }
}