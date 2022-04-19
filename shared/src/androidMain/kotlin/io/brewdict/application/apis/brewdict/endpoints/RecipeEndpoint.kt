package io.brewdict.application.apis.brewdict.endpoints

import io.brewdict.application.api_consumption.CRUDEndpoint
import io.brewdict.application.api_consumption.Result
import io.brewdict.application.apis.brewdict.BrewdictAPI
import io.brewdict.application.apis.brewdict.models.Recipe
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import java.io.IOException

object RecipeEndpoint : CRUDEndpoint<Recipe>(BrewdictAPI, "recipes", "recipes") {
    override fun index(): Result<List<Recipe>> {
        var result: Result<List<Recipe>>

        runBlocking {
            result = try {
                val recipes: List<Recipe> = api.client.get {
                    url(api.host + "/" + route)
                }

                Result.Success(recipes)
            } catch (e: Throwable) {
                Result.Error(IOException("Error retrieving recipes:", e))
            }
        }

        return result
    }

    override fun get (id: Int): Result<Recipe> {
        var result: Result<Recipe>

        runBlocking {
            result = try {
                val recipe: Recipe = api.client.get {
                    url(api.host + "/" + route + "/" + id)
                }

                Result.Success(recipe)
            } catch (e: Throwable) {
                Result.Error(IOException("Error retrieving recipe:", e))
            }
        }

        return result
    }

    override fun create (model: Recipe): Result<Recipe> {
        var result: Result<Recipe>

        runBlocking {
            result = try {
                val recipe: Recipe = api.client.post {
                    url(api.host + "/users/${BrewdictAPI.loggedInUser!!.user.id}/${route}")
                    parameter("name", model.name)
                    parameter("description", model.description)
                    parameter("inspiration", model.inspiration?.id)
                    parameter("style_id", model.style.id)
                    parameter("og", model.og)
                    parameter("fg", model.fg)
                    parameter("ibu", model.ibu)
                    parameter("srm", model.srm)
                }

                Result.Success(recipe)
            } catch (e: Throwable) {
                Result.Error(IOException("Error creating recipe:", e))
            }
        }

        return result
    }

    override fun update (id: Int?, model: Recipe): Result<Recipe> {
        val modelId = id ?: model.id
        var result: Result<Recipe>

        runBlocking {
            result = try {
                val recipe: Recipe = api.client.put {
                    url(api.host + "/" + route + "/" + modelId)
                    parameter("name", model.name)
                    parameter("description", model.description)
                    parameter("inspiration", model.inspiration?.id)
                    parameter("style_id", model.style.id)
                    parameter("og", model.og)
                    parameter("fg", model.fg)
                    parameter("ibu", model.ibu)
                    parameter("srm", model.srm)
                }

                Result.Success(recipe)
            } catch (e: Throwable) {
                Result.Error(IOException("Error updating recipe:", e))
            }
        }

        return result
    }

    override fun delete (id: Int): Result<Recipe?> {
        var result: Result<Recipe?>

        runBlocking {
            result = try {
                val response: HttpResponse = api.client.delete {
                    url(api.host + "/" + route + "/" + id)
                }

                if (response.status == HttpStatusCode.NoContent) {
                    Result.Success(null)
                }else {
                    Result.Error(IOException("Error deleting recipe."))
                }
            } catch (e: Throwable) {
                Result.Error(IOException("Error deleting in:", e))
            }
        }

        return result
    }
}