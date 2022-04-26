package io.brewdict.application.android.ui.fermentations.view

import io.brewdict.application.apis.brewdict.models.Fermentation

data class FermentationStartResult(
    val success: Fermentation? = null,
    val error: Int? = null
)
