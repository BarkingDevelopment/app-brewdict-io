package io.brewdict.application.android.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import io.brewdict.application.apis.brewdict.BrewdictAPI
import java.lang.IllegalArgumentException

class RegisterViewModelFactory : Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(
                api = BrewdictAPI
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }

}
