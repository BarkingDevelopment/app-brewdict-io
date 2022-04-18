package io.brewdict.application.api_consumption

import io.brewdict.application.api_consumption.models.Model

abstract class CRUDEndpoint<T : Model> (
    val api: API,
    val route: String,
    val type: String
) : Endpoint {
    abstract fun index(): Result<List<T>>

    abstract fun get (id: Int): Result<T>

    abstract fun create (model: T): Result<T>

    abstract fun update (id: Int?, model: T): Result<T>

    abstract fun delete (id: Int): Result<T?>
}