package io.brewdict.application.android.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.brewdict.application.apis.brewdict.BrewdictAPI

class ProfileViewModel : ViewModel() {
    private val _passwordResetResult = MutableLiveData<PasswordResetResult>()
    val passwordResetResult: LiveData<PasswordResetResult> = _passwordResetResult

    private val _logoutResult = MutableLiveData<LogoutResult>()
    val logoutResult: LiveData<LogoutResult> = _logoutResult

    fun resetPassword(password: String, retypePassword: String) {
        TODO("Not yet implemented")
    }

    fun logout() {
        val result = BrewdictAPI.logout()

        _logoutResult.value = LogoutResult(success = result != null)
    }



}