package io.brewdict.application.api_consumption.models

import io.brewdict.application.api_consumption.response.Model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Token(
    @SerialName("token_type") val tokenType: String,
    @SerialName("token") val accessToken: String,
    @SerialName("expires_in") val expiresIn: Int? = null,
    @SerialName("refresh_token") val refreshToken: String? = null,
) : Model
