package io.brewdict.application.apis.brewdict.models

import io.brewdict.application.api_consumption.models.Model
import io.brewdict.application.api_consumption.serializers.LocalDateTimeSerializer
import io.brewdict.application.apis.brewdict.ReadingTypeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Reading (
    @SerialName("id") val id: Int?,
    val fermentation: Fermentation? = null,
    @SerialName("recorded_at") @Serializable(LocalDateTimeSerializer::class) val recordedDatetime: LocalDateTime,
    @SerialName("type") @Serializable(ReadingTypeSerializer::class) val type: ReadingTypeEnum,
    @SerialName("value") val value: Float,
    @SerialName("units") val units: String,
) : Model
