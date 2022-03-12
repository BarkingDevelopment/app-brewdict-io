package io.brewdict.application.api_consumption

import io.brewdict.application.api_consumption.models.LoggedInUser
import io.brewdict.application.api_consumption.models.Token
import io.brewdict.application.api_consumption.response.LoginResponse
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import java.io.IOException

class AuthenticationEndpoint(val api: API, val path: String) : Endpoint{
    fun emailLogin(email: String, password: String): Result<LoggedInUser> {
        var result: Result<LoggedInUser>

        runBlocking {
            result = try {
                val user: LoggedInUser = api.client.post {
                    url(api.host + "/" + path)
                    parameter("email", email)
                    parameter("password", password) //TODO: Hash this before sending.
                }

                Result.Success(user)
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
                val response: LoginResponse = api.client.post {
                    url(api.host + "/" + path)
                    parameter("username", username)
                    parameter("password", password) //TODO: Hash this before sending.
                }

                Result.Success(LoggedInUser(response.data.attributes, response.related.tokenObject.data.attributes))
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