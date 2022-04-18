package io.brewdict.application.android.ui.fermentations

import io.brewdict.application.android.utils.SortingFieldEnum
import io.brewdict.application.apis.brewdict.models.Fermentation

enum class FermentationSortingFieldEnum : SortingFieldEnum<Fermentation> {
    NAME {
        override val acronym: String
            get() = "ABC"
        override val field: Fermentation.() -> Any
            get() = { recipe.name }
    },
    STYLE {
        override val acronym: String
            get() = "STYLE"
        override val field: Fermentation.() -> Any?
            get() = { recipe.style.name }
    },
    START {
        override val acronym: String
            get() = "DATE"
        override val field: Fermentation.() -> Any?
            get() = { startDatetime }
    },

}