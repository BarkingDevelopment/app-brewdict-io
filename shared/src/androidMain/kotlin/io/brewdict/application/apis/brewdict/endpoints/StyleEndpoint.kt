package io.brewdict.application.apis.brewdict.endpoints

import io.brewdict.application.api_consumption.CRUDEndpoint
import io.brewdict.application.api_consumption.Result
import io.brewdict.application.apis.brewdict.BrewdictAPI
import io.brewdict.application.apis.brewdict.models.Style
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import java.io.IOException

object StyleEndpoint: CRUDEndpoint<Style>(BrewdictAPI, "styles", "styles") {
    override fun index(): Result<List<Style>> {
        var result: Result<List<Style>>

        runBlocking {
            result = try {
                val style: List<Style> = api.client.get {
                    url(api.host + "/" + route)
                }

                Result.Success(style)
            } catch (e: Throwable) {
                Result.Error(IOException("Error retrieving styles:", e))
            }
        }

        return result
    }

    override fun create(model: Style): Result<Style> {
        throw UnsupportedOperationException("Resource path  denied.")
    }

    override fun get (id: Int): Result<Style> {
        var result: Result<Style>

        runBlocking {
            result = try {
                val style: Style = api.client.get {
                    url(api.host + "/" + route + "/" + id)
                }

                Result.Success(style)
            } catch (e: Throwable) {
                Result.Error(IOException("Error retrieving style:", e))
            }
        }

        return result
    }

    override fun update(id: Int?, model: Style): Result<Style> {
        throw UnsupportedOperationException("Resource path  denied.")
    }

    override fun delete(id: Int): Result<Style?> {
        throw UnsupportedOperationException("Resource path  denied.")
    }
}