package io.brewdict.application.android.ui.recipes.list

import io.brewdict.application.android.utils.SortingFieldEnum
import io.brewdict.application.apis.brewdict.models.Recipe

enum class RecipeSortingFieldEnum: SortingFieldEnum<Recipe> {
    NAME {
        override val acronym: String
            get() = "ABC"
        override val field: Recipe.() -> Any
            get() = { name }
    },
    STYLE {
        override val acronym: String
            get() = "STYLE"
        override val field: Recipe.() -> Any
            get() = { style.name }
    },
    ABV {
        override val acronym: String
            get() = "ABV"
        override val field: Recipe.() -> Any
            get() = { abv }
    },
    IBU() {
        override val acronym: String
            get() = "IBU"
        override val field: Recipe.() -> Any
            get() = { ibu }
    },
    SRM() {
        override val acronym: String
            get() = "SRM"
        override val field: Recipe.() -> Any
            get() = { srm }
    },
}