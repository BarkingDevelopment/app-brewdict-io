package io.brewdict.application.android.ui.recipes.styles

import io.brewdict.application.android.utils.MultiToggleButtonEnum

enum class StyleJournalEnum: MultiToggleButtonEnum {
    BA{
        override val string: String
            get() = "BA"

    },
    BJCP{
        override val string: String
            get() = "BJCP"

    },
    CAMRA{
        override val string: String
            get() = "CAMRA"

    },
}