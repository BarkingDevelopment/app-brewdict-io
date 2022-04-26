package io.brewdict.application.android.ui.fermentations.list

import io.brewdict.application.android.utils.MultiToggleButtonEnum

enum class FermentationCompleteStatusEnum : MultiToggleButtonEnum{
    CURRENT {
        override val string: String
            get() = "Current"
    },
    COMPLETE {
        override val string: String
            get() = "Completed"
    }
}