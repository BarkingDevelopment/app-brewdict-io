package io.brewdict.application.api_consumption.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

@Serializable
data class ResponseObjectContainer<T: Model> (
    @SerialName("data") val data : ResponseObject<T>
)