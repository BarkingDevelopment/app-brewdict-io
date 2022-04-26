package io.brewdict.application.android.ui.fermentations.readings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.brewdict.application.android.R
import io.brewdict.application.api_consumption.Result
import io.brewdict.application.apis.brewdict.BrewdictAPI
import io.brewdict.application.apis.brewdict.endpoints.ReadingEndpoint
import io.brewdict.application.apis.brewdict.models.Fermentation
import io.brewdict.application.apis.brewdict.models.Reading
import io.brewdict.application.apis.brewdict.models.ReadingTypeEnum
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class ReadingViewModel(var fermentation: Fermentation) : ViewModel() {
    private val _readingCreateResult = MutableLiveData<ReadingCreateResult>()
    val readingCreateResult: LiveData<ReadingCreateResult> = _readingCreateResult

    protected var list = MutableLiveData(fermentation.readings)
    val values: MutableLiveData<List<Reading>?> = list

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    fun refreshList() {
        viewModelScope.launch {
            _isRefreshing.emit(true)

            val result = BrewdictAPI.endpoints["fermentations"]!!.get(fermentation.id!!)

            if (result is Result.Success) {
                fermentation = result.data as Fermentation
                list.value = fermentation.readings
            }

            _isRefreshing.emit(false)
        }
    }

    fun createReading(type: ReadingTypeEnum, value: Float?, dateTime: LocalDateTime) {
        if (value == null) {
            _readingCreateResult.value = ReadingCreateResult(error = R.string.login_failed)
            return
        }

        val resource = BrewdictAPI.endpoints["readings"]!! as ReadingEndpoint

        val result = resource.create(
            Reading(
                id = null,
                fermentation = fermentation,
                recordedDatetime = dateTime,
                value = value,
                type = type,
                units = type.unit
            )
        )

        if (result is Result.Success) _readingCreateResult.value = ReadingCreateResult(success = result.data)
        else _readingCreateResult.value = ReadingCreateResult(error = R.string.login_failed)
    }
}