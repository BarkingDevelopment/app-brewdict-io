package io.brewdict.application.apis.brewdict

import io.brewdict.application.api_consumption.AuthenticatedAPI
import io.brewdict.application.apis.brewdict.endpoints.RecipeEndpoint
import io.brewdict.application.apis.brewdict.endpoints.StyleEndpoint

object BrewdictAPI : AuthenticatedAPI("https://api.brewdict.io", "login", "users/register") {
    init {
        this.addEndpoint(RecipeEndpoint)
        this.addEndpoint(StyleEndpoint)
    }
}