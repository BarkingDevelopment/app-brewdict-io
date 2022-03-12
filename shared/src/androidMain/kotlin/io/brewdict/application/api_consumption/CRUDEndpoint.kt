package io.brewdict.application.api_consumption

import io.brewdict.application.api_consumption.response.Model

abstract class CRUDEndpoint<T : Model> (
    val api: API,
    val route: String,
) : Endpoint {
    init {
        api.addEndpoint(this)
    }

    open suspend fun index(): Result<List<T>>{
        throw NotImplementedError()
    }

    open suspend fun get (id: Int): Result<T> {
        throw NotImplementedError()
    }

    open suspend fun create (model: T): Result<T>{
        throw NotImplementedError()
    }

    open suspend fun update (id: Int, model: T): Result<T>{
        throw NotImplementedError()
    }

    open suspend fun delete (id: Int): Result<T>{
        throw NotImplementedError()
    }
}