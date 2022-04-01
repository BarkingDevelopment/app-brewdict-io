package io.brewdict.application.api_consumption.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
 data class User(
    @SerialName("id") val id: Int?,
    @SerialName("email") val email: String,
    @SerialName("username") val username: String,
) : Model