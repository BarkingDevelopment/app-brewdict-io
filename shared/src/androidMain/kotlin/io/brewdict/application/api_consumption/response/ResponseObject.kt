package io.brewdict.application.api_consumption.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseObject<T: Model>(
    @SerialName("id") val id: Int?,
    @SerialName("attributes") val attributes: T
)
