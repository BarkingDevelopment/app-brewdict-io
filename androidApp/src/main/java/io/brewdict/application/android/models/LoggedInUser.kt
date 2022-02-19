package io.brewdict.application.android.models

// TODO Align with API outputs.
data class LoggedInUser(
    val id: String,
    val email: String,
    val username: String,
    val token: String
)