package io.brewdict.application.android.ui.fermentations.readings

import io.brewdict.application.apis.brewdict.models.Reading

data class ReadingCreateResult(
    val success: Reading? = null,
    val error: Int? = null
)
