package io.brewdict.application.android.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import io.brewdict.application.apis.brewdict.BrewdictAPI
import java.lang.IllegalArgumentException

class LoginViewModelFactory : Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                api = BrewdictAPI
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }

}
