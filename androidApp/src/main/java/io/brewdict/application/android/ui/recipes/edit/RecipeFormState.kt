package io.brewdict.application.android.ui.recipes.edit

data class RecipeFormState(
    val ogError: Int? = null,
    val fgError: Int? = null,
    val isDataValid: Boolean = false,
)
