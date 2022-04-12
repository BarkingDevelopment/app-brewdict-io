package io.brewdict.application.android.ui.recipes.list

import io.brewdict.application.android.utils.MultiToggleButtonEnum

enum class RecipeOwnerMultiToggleEnum : MultiToggleButtonEnum {
    ALL{
        override val string: String
            get() = "Public Recipes"

    },
    PRIVATE{
        override val string: String
            get() = "Your Recipes"

    }
}