package io.brewdict.application.android.ui.recipes.list

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.brewdict.application.api_consumption.models.User
import io.brewdict.application.apis.brewdict.models.Recipe
import io.brewdict.application.apis.brewdict.models.Style
import io.brewdict.application.apis.brewdict.models.StyleCategory

class SampleRecipeProvider: PreviewParameterProvider<Recipe> {
    override val values = sequenceOf(
        Recipe(
            id = 1,
            owner = User(
                id = 1,
                username = "mlbarker",
                email = "hello@maxbarker.uk"
            ),
            name = "Test Recipe",
            description = "This is a test recipe!",
            inspiration = null,
            style =  Style (
                id = 1,
                name = "IPA",
                category = StyleCategory(
                    id = 1,
                    name = "Pale Ale",
                    description = "An ale that is pale.",
                    number = 8,
                    guide = "Guide"
                ),
                letter = 'A',
                type = "ale",
                minOG = 1.05f,
                maxOG = 1.08f,
                minFG = 1.008f,
                maxFG = 1.015f,
                minIBU = 20f,
                maxIBU = 120f,
                minSRM = 8f,
                maxSRM = 30f,
                notes = "This is an example style!"
            ),
            abv = 5.0f,
            ibu = 50,
            srm = 8
        )
    )
}