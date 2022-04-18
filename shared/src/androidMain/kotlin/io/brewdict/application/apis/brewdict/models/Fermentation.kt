package io.brewdict.application.apis.brewdict.models

import io.brewdict.application.api_consumption.models.Model
import io.brewdict.application.api_consumption.models.User
import io.brewdict.application.api_consumption.serializers.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Fermentation(
    @SerialName("id") val id: Int?,
    @SerialName("recipe") val recipe: Recipe,
    @SerialName("brewer") val brewer: User,
    @SerialName("completed") val isComplete: Boolean,
    @SerialName("started_at") @Serializable(DateSerializer::class) val startDatetime: Date? = null,
    @SerialName("readings") val readings : List<Reading>? = null
) : Model
