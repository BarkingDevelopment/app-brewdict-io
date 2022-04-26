package io.brewdict.application.apis.brewdict.models

import io.brewdict.application.api_consumption.models.Model
import io.brewdict.application.api_consumption.models.User
import io.brewdict.application.api_consumption.serializers.LocalDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Fermentation(
    @SerialName("id") val id: Int?,
    @SerialName("recipe") val recipe: Recipe,
    @SerialName("brewer") val brewer: User,
    @SerialName("og") val og: Float? = null,
    @SerialName("started_at") @Serializable(LocalDateTimeSerializer::class) val startDatetime: LocalDateTime? = null,
    @SerialName("completed") val isComplete: Boolean,
    @SerialName("readings") val readings : List<Reading>? = null
) : Model{
    val specificGravities: List<Reading>?
        get() = readings?.filter { r -> r.type == ReadingTypeEnum.SG }

    val temperatures: List<Reading>?
        get() = readings?.filter { r -> r.type == ReadingTypeEnum.TEMP }

    val isStarted: Boolean
        get () = startDatetime != null

    val fg: Float?
        get() = if (isComplete) specificGravities?.maxOf { it.value }
            else((og?.minus(1f))?.div(4))?.plus(1f)

    val abv: Float?
        get() = (fg?.let { og?.minus(it) })?.times(131.25.toFloat())
}
