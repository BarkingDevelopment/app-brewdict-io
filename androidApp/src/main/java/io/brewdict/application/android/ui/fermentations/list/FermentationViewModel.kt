package io.brewdict.application.android.ui.fermentations.list

import io.brewdict.application.android.utils.IndexResourceViewModel
import io.brewdict.application.apis.brewdict.BrewdictAPI
import io.brewdict.application.apis.brewdict.models.Fermentation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FermentationViewModel : IndexResourceViewModel<Fermentation, FermentationSortingFieldEnum>(
    _fnIndex = { BrewdictAPI.endpoints["fermentations"]!!.index() },
    _fieldFilter = { recipe.name },
    sortingFields = FermentationSortingFieldEnum.values(),
    defaultSortingField = FermentationSortingFieldEnum.NAME
) {
    private val _showCompleteFermentations = MutableStateFlow(false)
    val showCompleteFermentations: StateFlow<Boolean>
        get() = _showCompleteFermentations.asStateFlow()

    init {
        refreshList()
    }

    fun toggleShowCompletedFermentations(flag: Boolean) {
        _showCompleteFermentations.value = flag
        updateList()
    }

    private fun filterIfComplete(list: List<Fermentation>): List<Fermentation>{
        return list.filter {
            it.isComplete == _showCompleteFermentations.value
        }
    }

    override fun updateList(){
        super.updateList()

        list.value = filterIfComplete(list.value!!)
    }
}