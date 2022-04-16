package io.brewdict.application.android.ui.recipes.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.brewdict.application.android.R
import io.brewdict.application.api_consumption.Result
import io.brewdict.application.apis.brewdict.BrewdictAPI
import io.brewdict.application.apis.brewdict.endpoints.RecipeEndpoint
import io.brewdict.application.apis.brewdict.models.Recipe

class RecipeViewModel : ViewModel() {
    private val _recipeDeleteResult = MutableLiveData<RecipeDeleteResult>()
    val recipeDeleteResult: LiveData<RecipeDeleteResult> = _recipeDeleteResult

    var recipe: Recipe? = null

    fun deleteRecipe(recipe: Recipe) {
        val resource = BrewdictAPI.endpoints["recipes"]!! as RecipeEndpoint

        val result = resource.delete(
            id = recipe.id!!
        )

        if (result is Result.Success) _recipeDeleteResult.value = RecipeDeleteResult(success = true)
        else _recipeDeleteResult.value = RecipeDeleteResult(error = R.string.login_failed)
    }
}