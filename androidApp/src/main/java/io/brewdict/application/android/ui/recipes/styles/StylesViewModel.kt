package io.brewdict.application.android.ui.recipes.styles

import io.brewdict.application.android.utils.IndexResourceViewModel
import io.brewdict.application.apis.brewdict.BrewdictAPI
import io.brewdict.application.apis.brewdict.models.Style
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class StylesViewModel : IndexResourceViewModel<Style, StyleSortingFieldEnum>(
    _fnIndex = { BrewdictAPI.endpoints["styles"]!!.index() },
    _fieldFilter = { name },
    sortingFields = StyleSortingFieldEnum.values(),
    defaultSortingField = StyleSortingFieldEnum.NAME
) {
    private val _journal = MutableStateFlow(StyleJournalEnum.BA)
    val journal: StateFlow<StyleJournalEnum>
        get() = _journal.asStateFlow()

    init {
        refreshList()
    }

    fun changeStyleJournal(journal: StyleJournalEnum){
        _journal.value = journal
        updateList()
    }

    private fun filterByJournal(list: List<Style>): List<Style>{
        return list.filter { it.category.guide == _journal.value.string }
    }

    override fun updateList(){
        super.updateList()

        list.value = filterByJournal(list.value!!)
    }
}