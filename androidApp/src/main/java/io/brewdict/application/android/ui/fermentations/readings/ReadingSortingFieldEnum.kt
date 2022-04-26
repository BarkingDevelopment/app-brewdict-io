package io.brewdict.application.android.ui.fermentations.readings

import io.brewdict.application.android.utils.SortingFieldEnum
import io.brewdict.application.apis.brewdict.models.Reading

enum class ReadingSortingFieldEnum : SortingFieldEnum<Reading> {
    DATE {
        override val acronym: String
            get() = TODO("Not yet implemented")
        override val field: Reading.() -> Any?
            get() = { recordedDatetime }

    }
}
