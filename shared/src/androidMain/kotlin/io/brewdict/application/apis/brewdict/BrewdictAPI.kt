package io.brewdict.application.apis.brewdict

import io.brewdict.application.api_consumption.AuthenticatedAPI

object BrewdictAPI : AuthenticatedAPI("https://api.brewdict.io", "login", "users/register") {
    init {
        //TODO Add endpoints.
    }
}