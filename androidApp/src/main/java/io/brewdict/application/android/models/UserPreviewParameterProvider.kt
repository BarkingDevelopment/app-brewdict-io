package io.brewdict.application.android.models

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.brewdict.application.api_consumption.models.User

class UserPreviewParameterProvider : PreviewParameterProvider<User> {
    override val values = sequenceOf(
        User("", "mlbarker")
    )
}