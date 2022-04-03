package io.brewdict.application.android.ui.recipes.listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.brewdict.application.api_consumption.Result
import io.brewdict.application.apis.brewdict.BrewdictAPI
import io.brewdict.application.apis.brewdict.models.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipesViewModel : ViewModel() {
    var showPublicRecipes: Boolean = true
    var sortingField: RecipeSortingField = RecipeSortingField.NAME
    var isSortingAscending: Boolean = true
    var filterString: String = ""

    private var _rawRecipes: List<Recipe> = listOf()

    private var _recipes = MutableLiveData(listOf<Recipe>())
    val recipes: LiveData<List<Recipe>> = _recipes

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    init {
        refreshRecipes()
    }

    fun refreshRecipes() {
        viewModelScope.launch {
            _isRefreshing.emit(true)

            val result = BrewdictAPI.endpoints["recipes"]?.index()

            if (result is Result.Success) {
                _rawRecipes = result.data as ArrayList<Recipe>

                updateRecipes()
            }

            _isRefreshing.emit(false)
        }
    }

    private fun filterRecipes(): List<Recipe>{
        var recipes = if (showPublicRecipes) _rawRecipes
            else _rawRecipes.filter { it.owner.id == BrewdictAPI.loggedInUser?.user!!.id }

        recipes = if (filterString.isNotEmpty()) recipes.filter { it.name.lowercase().startsWith(filterString) }
            else recipes

        return recipes
    }

    fun <T : Comparable<T>> sortUsing(recipes: List<Recipe>, fn: Recipe.() -> T, ascending: Boolean): List<Recipe> {
        return if (ascending) recipes.sortedBy { it.fn() } else recipes.sortedByDescending { it.fn() }
    }

    private fun sortRecipes(recipes: List<Recipe>): List<Recipe>{
        val sortedRecipes = when(sortingField){
            RecipeSortingField.NAME -> sortUsing(recipes, {name}, isSortingAscending)
            RecipeSortingField.STYLE -> sortUsing(recipes, {style.name}, isSortingAscending)
            RecipeSortingField.ABV -> sortUsing(recipes, {abv}, isSortingAscending)
            RecipeSortingField.IBU -> sortUsing(recipes, {ibu}, isSortingAscending)
            RecipeSortingField.SRM -> sortUsing(recipes, {srm}, isSortingAscending)
        }

        return sortedRecipes
    }

    fun updateRecipes(){
        var recipes = filterRecipes()

        _recipes.value =  sortRecipes(recipes)
    }
}