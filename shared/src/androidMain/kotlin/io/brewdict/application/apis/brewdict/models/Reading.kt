package io.brewdict.application.apis.brewdict.models

import io.brewdict.application.api_consumption.models.Model
import io.brewdict.application.api_consumption.serializers.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Reading(
    @SerialName("id") val id: Int?,
    @SerialName("recorded_at") @Serializable(DateSerializer::class) val recordedDatetime: Date,
    @SerialName("type") val type: String,
    @SerialName("value") val value: Float,
    @SerialName("units") val units: String,
) : Model
