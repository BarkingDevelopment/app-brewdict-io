package io.brewdict.application.android.utils

import androidx.compose.animation.core.updateTransition
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.brewdict.application.api_consumption.Result
import io.brewdict.application.api_consumption.models.Model
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class IndexResourceViewModel<T : Model, S : SortingFieldEnum<T>>(
    private val _fnIndex: () -> Result<*>,
    private val _fieldFilter: T.() -> String,
    val sortingFields: Array<S>,
    defaultSortingField: S?
) : ViewModel() {
    private val _currentSortingField = MutableStateFlow(defaultSortingField ?: sortingFields[0])
    val currentSortingField: StateFlow<S>
        get() = _currentSortingField.asStateFlow()

    private val _isSortingAscending = MutableStateFlow(true)
    val isSortingAscending: StateFlow<Boolean>
        get() = _isSortingAscending.asStateFlow()

    private val _filterString = MutableStateFlow("")
    val filterString: StateFlow<String>
        get() = _filterString.asStateFlow()

    private var _rawList: List<T> = listOf()
    protected var list = MutableLiveData(listOf<T>())
    val values: LiveData<List<T>> = list

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    fun invertSortingDirection(){
        _isSortingAscending.value = !_isSortingAscending.value
        updateList()
    }

    fun changeSortingField(field: S){
        _currentSortingField.value = field
        updateList()
    }

    fun updateFilterString(string: String){
        _filterString.value = string
        updateList()
    }

    @SuppressWarnings("unchecked")
    private fun sortUsing(
        list: List<T>,
        field: T.() -> Any?,
        ascending: Boolean
    ): List<T> {
        // FIXME:   Abstract Comparable<Any> out of sort functions. This can be done using
        //          <V: Comparable<V>> but am unsure how this works with the way I've done enums.
        //          It works tho so... can't complain.
        return if (ascending) list.sortedBy { it.field() as Comparable<Any?> }
            else list.sortedByDescending { it.field() as Comparable<Any?> }
    }

    fun refreshList() {
        viewModelScope.launch {
            _isRefreshing.emit(true)

            val result = _fnIndex()

            if (result is Result.Success) {
                _rawList = result.data as ArrayList<T>

                updateList()
            }

            _isRefreshing.emit(false)
        }
    }

    private fun filterList(list: List<T>): List<T> =
        if (_filterString.value.isNotEmpty()) list.filter {
            it._fieldFilter().lowercase().startsWith(_filterString.value)
        }
        else list

    private fun sortList(list: List<T>): List<T> =
        sortUsing(list, _currentSortingField.value.field, _isSortingAscending.value)

    open fun updateList(){
        list.value = _rawList
        val l = filterList(list.value!!)

        list.value =  sortList(l)
    }
}