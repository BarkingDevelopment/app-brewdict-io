package io.brewdict.application.android.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import io.brewdict.application.android.models.LoginDataSource
import io.brewdict.application.android.models.LoginRepository
import java.lang.IllegalArgumentException

class LoginViewModelFactory : Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                repo = LoginRepository(dataSource = LoginDataSource())
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }

}
