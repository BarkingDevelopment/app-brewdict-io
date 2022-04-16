package io.brewdict.application.android.ui.recipes.edit

import io.brewdict.application.apis.brewdict.models.Recipe

data class RecipeSaveResult(
    val success: Recipe? = null,
    val error: Int? = null
)
