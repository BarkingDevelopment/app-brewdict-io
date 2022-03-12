package io.brewdict.application.android.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.brewdict.application.android.R
import io.brewdict.application.api_consumption.Result
import io.brewdict.application.api_consumption.AuthenticatedAPI

class LoginViewModel(private val api: AuthenticatedAPI) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(identity: String, password: String){
        val result = api.login(identity, password)

        if (result is Result.Success) _loginResult.value = LoginResult(success = result.data)
        else _loginResult.value = LoginResult(error = R.string.login_failed)
    }

    fun loginDataChanged(identity: String, password: String){
        if(!isIdentityValid(identity)) _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        else if (!isPasswordValid(password)) _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        else _loginForm.value = LoginFormState(isDataValid = true)
    }

    private fun isIdentityValid(identity: String): Boolean {
        return if (identity.contains("@"))  Patterns.EMAIL_ADDRESS.matcher(identity).matches()
        else  identity.isNotBlank()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 7
    }
}
