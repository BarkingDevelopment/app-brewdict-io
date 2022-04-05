package io.brewdict.application.android.ui.recipes.view

import androidx.lifecycle.ViewModel
import io.brewdict.application.apis.brewdict.models.Recipe

class RecipeViewModel : ViewModel() {
    var recipe: Recipe? = null
}