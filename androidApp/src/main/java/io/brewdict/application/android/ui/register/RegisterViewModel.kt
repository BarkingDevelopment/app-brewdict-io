package io.brewdict.application.android.ui.register

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.brewdict.application.android.R
import io.brewdict.application.api_consumption.Result
import io.brewdict.application.api_consumption.AuthenticatedAPI
import io.brewdict.application.api_consumption.models.User

class RegisterViewModel(private val api: AuthenticatedAPI) : ViewModel() {

    private val _registrationForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registrationForm

    private val _registrationResult = MutableLiveData<RegistrationResult>()
    val registrationResult: LiveData<RegistrationResult> = _registrationResult

    fun register(email: String, username: String, password: String){

        val result = api.register(
            User(null, email, username),
            password
        )

        if (result is Result.Success) _registrationResult.value = RegistrationResult(success = result.data)
        else _registrationResult.value = RegistrationResult(error = R.string.login_failed)
    }

    fun loginDataChanged(identity: String, password: String){
        if(!isIdentityValid(identity)) _registrationForm.value = RegisterFormState(usernameError = R.string.invalid_username)
        else if (!isPasswordValid(password)) _registrationForm.value = RegisterFormState(passwordError = R.string.invalid_password)
        else _registrationForm.value = RegisterFormState(isDataValid = true)
    }

    private fun isIdentityValid(identity: String): Boolean {
        return if (identity.contains("@"))  Patterns.EMAIL_ADDRESS.matcher(identity).matches()
        else  identity.isNotBlank()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 7
    }
}
