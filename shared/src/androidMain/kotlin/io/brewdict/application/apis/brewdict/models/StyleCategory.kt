package io.brewdict.application.apis.brewdict.models

import io.brewdict.application.api_consumption.models.Model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StyleCategory(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("number") val number: Int,
    @SerialName("style_guide") val guide: String,
) : Model
