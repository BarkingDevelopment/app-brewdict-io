package io.brewdict.application.android.ui.recipes.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.brewdict.application.android.R
import io.brewdict.application.api_consumption.Result
import io.brewdict.application.apis.brewdict.BrewdictAPI
import io.brewdict.application.apis.brewdict.endpoints.RecipeEndpoint
import io.brewdict.application.apis.brewdict.models.Recipe
import io.brewdict.application.apis.brewdict.models.Style

class RecipeViewModel(): ViewModel() {

    private val _recipeForm = MutableLiveData<RecipeFormState>()
    val recipeFormState: LiveData<RecipeFormState> = _recipeForm

    private val _recipeSaveResult = MutableLiveData<RecipeSaveResult>()
    val recipeSaveResult: LiveData<RecipeSaveResult> = _recipeSaveResult

    var inspiration: Recipe? = null
        set(value) {
            field = value
            if (value != null) {
                resetAttributes()
                setAttributes(value)
            }
        }
    var recipeId = MutableLiveData<Int?>(null)
    var recipeName = MutableLiveData("")
    var recipeDesc =  MutableLiveData("")
    var recipeStyle =  MutableLiveData<Style?>(null)
    var recipeOG = MutableLiveData(1f)
    var recipeFG = MutableLiveData(1f)
    var recipeIbu = MutableLiveData(1)
    var recipeSrm = MutableLiveData(1)

    fun setEditedRecipe(recipe: Recipe?){
        if (recipe != null){
            resetAttributes()
            recipeId.value = recipe.id!!
            setAttributes(recipe)
        }else{
            resetAttributes()
        }
    }

    private fun resetAttributes(){
        recipeId.value = null
        recipeName.value= ""
        recipeDesc.value = ""
        recipeStyle.value = null
        recipeOG.value = 1f
        recipeFG.value = 1f
        recipeIbu.value = 1
        recipeSrm.value = 1
    }

    private fun setAttributes(recipe: Recipe){
        recipeName.value= recipe.name
        recipeDesc.value = recipe.description
        recipeStyle.value = recipe.style
        recipeOG.value = recipe.og
        recipeFG.value = recipe.fg
        recipeIbu.value = recipe.ibu
        recipeSrm.value = recipe.srm
    }

    fun save(){
        val resource = BrewdictAPI.endpoints["recipes"]!! as RecipeEndpoint

        val result: Result<Recipe>
        if(recipeId.value == null){
             result = resource.create(
                Recipe(
                    id = null,
                    owner = BrewdictAPI.loggedInUser!!.user,
                    name = recipeName.value!!,
                    description = recipeDesc.value!!,
                    inspiration = inspiration,
                    style = recipeStyle.value!!,
                    og = recipeOG.value!!,
                    fg = recipeFG.value!!,
                    ibu = recipeIbu.value!!,
                    srm = recipeSrm.value!!,
                )
            )

        }else{
            result = resource.update(
                id = recipeId.value,
                model = Recipe(
                    id =  recipeId.value,
                    owner = BrewdictAPI.loggedInUser!!.user,
                    name = recipeName.value!!,
                    description = recipeDesc.value!!,
                    inspiration = inspiration,
                    style = recipeStyle.value!!,
                    og = recipeOG.value!!,
                    fg = recipeFG.value!!,
                    ibu = recipeIbu.value!!,
                    srm = recipeSrm.value!!,
                )
            )
        }

        if (result is Result.Success) _recipeSaveResult.value = RecipeSaveResult(success = result.data)
        else _recipeSaveResult.value = RecipeSaveResult(error = R.string.login_failed)
    }
}