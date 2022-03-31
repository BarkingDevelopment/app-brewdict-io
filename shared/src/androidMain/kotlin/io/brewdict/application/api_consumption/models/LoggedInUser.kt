package io.brewdict.application.api_consumption.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO Align with API outputs.
@Serializable
data class LoggedInUser(
    @SerialName("user") val user: User,
    @SerialName("token") val token: String,
)