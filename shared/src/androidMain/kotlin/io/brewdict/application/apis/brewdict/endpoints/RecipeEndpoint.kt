package io.brewdict.application.apis.brewdict.endpoints

import io.brewdict.application.api_consumption.CRUDEndpoint
import io.brewdict.application.api_consumption.Result
import io.brewdict.application.apis.brewdict.BrewdictAPI
import io.brewdict.application.apis.brewdict.models.Recipe
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import java.io.IOException

object RecipeEndpoint : CRUDEndpoint<Recipe>(BrewdictAPI, "recipes", "recipes") {
    override suspend fun index(): Result<List<Recipe>> {
        var result: Result<List<Recipe>>

        runBlocking {
            result = try {
                val recipes: List<Recipe> = api.client.get {
                    url(api.host + "/" + route)
                }

                Result.Success(recipes)
            } catch (e: Throwable) {
                Result.Error(IOException("Error logging in:", e))
            }
        }

        return result
    }

    override suspend fun get (id: Int): Result<Recipe> {
        var result: Result<Recipe>

        runBlocking {
            result = try {
                val recipe: Recipe = api.client.get {
                    url(api.host + "/" + route + "/" + id)
                }

                Result.Success(recipe)
            } catch (e: Throwable) {
                Result.Error(IOException("Error logging in:", e))
            }
        }

        return result
    }

    override suspend fun create (model: Recipe): Result<Recipe> {
        var result: Result<Recipe>

        throw NotImplementedError()

        return result
    }

    override suspend fun update (id: Int?, model: Recipe): Result<Recipe> {
        val id = id ?: model.id
        var result: Result<Recipe>

        runBlocking {
            result = try {
                val recipe: Recipe = api.client.post {
                    url(api.host + "/" + route + "/" + id)
                }

                Result.Success(recipe)
            } catch (e: Throwable) {
                Result.Error(IOException("Error logging in:", e))
            }
        }

        return result}

    override suspend fun delete (id: Int): Result<Recipe> {
        var result: Result<Recipe>

        runBlocking {
            result = try {
                val recipe: Recipe = api.client.delete {
                    url(api.host + "/" + route + "/" + id)
                }

                Result.Success(recipe)
            } catch (e: Throwable) {
                Result.Error(IOException("Error logging in:", e))
            }
        }

        return result
    }
}