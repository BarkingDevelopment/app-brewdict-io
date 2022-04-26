package io.brewdict.application.android.ui.fermentations.readings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.brewdict.application.apis.brewdict.models.Fermentation


class ViewModelFactory(private val fermentation: Fermentation) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReadingViewModel::class.java)) {
            return ReadingViewModel(fermentation) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}