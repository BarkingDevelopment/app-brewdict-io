package io.brewdict.application.api_consumption

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*

abstract class API(
    val host: String
) {
    var endpoints: ArrayList<Endpoint> = ArrayList()

    protected val unauthClient : HttpClient = HttpClient {
        install(JsonFeature){
            install(Logging) {
                level = LogLevel.ALL
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 15000L
                socketTimeoutMillis = 15000L
            }

            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                isLenient = true
                encodeDefaults = false
            })
        }
    }

    var client : HttpClient = unauthClient

    fun addEndpoint(endpoint: Endpoint){
        endpoints.add(endpoint)
    }
}