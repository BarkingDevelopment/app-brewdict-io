package io.brewdict.application.android.ui.recipes

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
    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    var recipes: ArrayList<Recipe> = ArrayList()

    fun refresh(){
        viewModelScope.launch {
            _isRefreshing.emit(true)

            val result = BrewdictAPI.endpoints["recipes"]?.index()

            if (result is Result.Success) {
                recipes = result.data as ArrayList<Recipe>
            }

            _isRefreshing.emit(false)
        }
    }
}