package io.brewdict.application.android.ui.recipes.view

import io.brewdict.application.apis.brewdict.models.Fermentation

data class FermentationCreateResult (
    val success: Fermentation? = null,
    val error: Int? = null
)