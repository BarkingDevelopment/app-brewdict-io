package io.brewdict.application.android.ui.recipes.view

import io.brewdict.application.apis.brewdict.models.Recipe

data class RecipeDeleteResult (
    val success: Boolean? = null,
    val error: Int? = null
)