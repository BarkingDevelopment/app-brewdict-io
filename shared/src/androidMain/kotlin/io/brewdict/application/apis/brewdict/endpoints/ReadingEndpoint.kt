package io.brewdict.application.apis.brewdict.endpoints

import android.os.Build
import androidx.annotation.RequiresApi
import io.brewdict.application.api_consumption.CRUDEndpoint
import io.brewdict.application.api_consumption.Result
import io.brewdict.application.apis.brewdict.BrewdictAPI
import io.brewdict.application.apis.brewdict.models.Fermentation
import io.brewdict.application.apis.brewdict.models.Reading
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import java.io.IOException
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

object ReadingEndpoint : CRUDEndpoint<Reading>(BrewdictAPI, "readings", "readings") {
    override fun index(): Result<List<Reading>> {
        TODO("Not yet implemented")
    }

    override fun get(id: Int): Result<Reading> {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun create(model: Reading): Result<Reading> {
        var result: Result<Reading>

        runBlocking {
            result = try {
                val reading: Reading = FermentationEndpoint.api.client.post {
                    url("${FermentationEndpoint.api.host}/fermentations/${model.fermentation!!.id}/${route}")
                    parameter("type", model.type.key)
                    parameter("recorded_at", model.recordedDatetime.withSecond(0).format(
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        )
                    )
                    parameter("value", model.value)
                    parameter("units", model.units)
                }

                Result.Success(reading)
            } catch (e: Throwable) {
                Result.Error(IOException("Error creating fermentation:", e))
            }
        }

        return result
    }

    override fun update(id: Int?, model: Reading): Result<Reading> {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int): Result<Reading?> {
        var result: Result<Reading?>

        runBlocking {
            result = try {
                val response: HttpResponse = RecipeEndpoint.api.client.delete {
                    url(api.host + "/" + route + "/" + id)
                }

                if (response.status == HttpStatusCode.NoContent) {
                    Result.Success(null)
                }else {
                    Result.Error(IOException("Error deleting recipe."))
                }
            } catch (e: Throwable) {
                Result.Error(IOException("Error deleting in:", e))
            }
        }

        return result
    }
}