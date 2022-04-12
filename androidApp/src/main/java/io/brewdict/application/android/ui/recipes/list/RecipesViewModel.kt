package io.brewdict.application.android.ui.recipes.list

import io.brewdict.application.android.utils.IndexResourceViewModel
import io.brewdict.application.apis.brewdict.BrewdictAPI
import io.brewdict.application.apis.brewdict.models.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RecipesViewModel : IndexResourceViewModel<Recipe, RecipeSortingFieldEnum>(
    _fnIndex = { BrewdictAPI.endpoints["recipes"]!!.index() },
    _fieldFilter = { name },
    sortingFields = RecipeSortingFieldEnum.values(),
    defaultSortingField = RecipeSortingFieldEnum.NAME
) {
    private val _showPublicRecipes = MutableStateFlow(true)
    val showPublicRecipes: StateFlow<Boolean>
        get() = _showPublicRecipes.asStateFlow()

    init {
        refreshList()
    }

    fun toggleShowPublicRecipes(flag: Boolean){
        _showPublicRecipes.value = flag
        updateList()
    }

    private fun filterIfOwnedByUser(list: List<Recipe>): List<Recipe>{
        return if (_showPublicRecipes.value) list
            else list.filter {
                it.owner.id == BrewdictAPI.loggedInUser?.user!!.id
            }
    }

    override fun updateList(){
        super.updateList()

        list.value = filterIfOwnedByUser(list.value!!)
    }
}