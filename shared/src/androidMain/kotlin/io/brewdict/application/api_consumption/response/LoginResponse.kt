package io.brewdict.application.api_consumption.response

import io.brewdict.application.api_consumption.models.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("data") val data : ResponseObject<User>,
    @SerialName("related") val related : TokenResponseObjectTypeContainer
    )
