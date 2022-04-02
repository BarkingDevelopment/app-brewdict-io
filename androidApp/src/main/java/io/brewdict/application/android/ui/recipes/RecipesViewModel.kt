package io.brewdict.application.android.ui.recipes

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
                _recipes.value = result.data as ArrayList<Recipe>
                sortUsing({name}, true)
            }

            _isRefreshing.emit(false)
        }
    }

    fun <T : Comparable<T>> sortUsing(fn: Recipe.() -> T, ascending: Boolean) {
        if (ascending){
            _recipes.value = _recipes.value?.sortedBy { it.fn() }
        } else {
            _recipes.value = _recipes.value?.sortedByDescending { it.fn() }
        }
    }
}