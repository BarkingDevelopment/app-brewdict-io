package io.brewdict.application.android.ui.recipes.styles

import io.brewdict.application.android.utils.SortingFieldEnum
import io.brewdict.application.apis.brewdict.models.Style

enum class StyleSortingFieldEnum: SortingFieldEnum<Style> {
    NAME {
        override val acronym: String
            get() = "ABC"
        override val field: Style.() -> Any
            get() = { name }
    },
}