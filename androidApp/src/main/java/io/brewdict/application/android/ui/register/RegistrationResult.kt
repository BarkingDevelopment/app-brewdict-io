package io.brewdict.application.android.ui.register

import io.brewdict.application.api_consumption.models.LoggedInUser

//TODO Abstract results into a results class.
data class RegistrationResult (
    val success: LoggedInUser? = null,
    val error: Int? = null
)
