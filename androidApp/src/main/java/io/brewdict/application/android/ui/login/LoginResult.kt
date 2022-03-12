package io.brewdict.application.android.ui.login

import io.brewdict.application.api_consumption.models.LoggedInUser

//TODO Abstract results into a results class.
data class LoginResult (
    val success: LoggedInUser? = null,
    val error: Int? = null
)
