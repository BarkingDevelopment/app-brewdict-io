package io.brewdict.application.api_consumption.response

import io.brewdict.application.api_consumption.models.Token
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponseObjectTypeContainer(
    @SerialName("access_token") val tokenObject : ResponseObjectContainer<Token>
)
