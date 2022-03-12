package io.brewdict.application.api_consumption.models

import io.brewdict.application.api_consumption.response.Model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
 data class User(
    @SerialName("email") val email: String,
    @SerialName("username") val username: String,
) : Model