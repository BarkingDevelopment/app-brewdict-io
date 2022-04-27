package io.brewdict.application.apis.brewdict.models

import android.os.Build
import androidx.annotation.RequiresApi
import io.brewdict.application.api_consumption.models.Model
import io.brewdict.application.api_consumption.models.User
import io.brewdict.application.api_consumption.serializers.LocalDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Serializable
data class Fermentation(
    @SerialName("id") val id: Int?,
    @SerialName("recipe") val recipe: Recipe,
    @SerialName("brewer") val brewer: User,
    //@SerialName("og") val og: Float? = null,
    @SerialName("started_at") @Serializable(LocalDateTimeSerializer::class) val startDatetime: LocalDateTime? = null,
    @SerialName("completed") val isComplete: Boolean,
    @SerialName("readings") val readings : List<Reading>? = null
) : Model{
    val specificGravities: List<Reading>
        get() {
            return if (!readings.isNullOrEmpty()) readings.filter { r -> r.type == ReadingTypeEnum.SG }
            else listOf()
        }

    val og: Float
        get() = specificGravities.minByOrNull { it.recordedDatetime }!!.value

    val sg: Float
        get() = specificGravities.maxByOrNull { it.recordedDatetime }!!.value

    val fg: Float
        get() = if (isComplete) sg
        else ((og.minus(1f)).div(4)).plus(1f)

    val abv: Float
        get() = og.minus(fg).times(131.25.toFloat())

    val temperatures: List<Reading>
        get(){
            return if (!readings.isNullOrEmpty()) readings.filter { r -> r.type == ReadingTypeEnum.TEMP }
            else listOf()
        }

    val startTemp: Float
        get() = temperatures.minByOrNull { it.recordedDatetime }!!.value

    val currentTemp: Float
        get() = temperatures.maxByOrNull { it.recordedDatetime }!!.value

    val meanTemp: Float
        get() = temperatures.map { it.value }.average().toFloat()

    val isStarted: Boolean
        get () = startDatetime != null

    val fermenetationTime: Float
        @RequiresApi(Build.VERSION_CODES.O)
        get() {
            readings!!

            return ChronoUnit.HOURS.between(
                readings.minOf { it.recordedDatetime },
                readings.maxOf { it.recordedDatetime }
            ).toFloat()
        }

}
