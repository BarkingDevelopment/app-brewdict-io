package io.brewdict.application.android.ui.recipes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.brewdict.application.android.utils.SharedComponents.Accordion
import io.brewdict.application.android.utils.SharedComponents.Range
import io.brewdict.application.apis.brewdict.models.Recipe
import io.brewdict.application.apis.brewdict.models.Style
import java.text.DecimalFormat
import java.text.NumberFormat
import kotlin.math.ceil
import kotlin.math.floor

object RecipeComponents {
    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    @Composable()
    fun StyleAccordion(style: Style){
        Accordion(
            header = {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = "${style.category.guide}: ${style.category.number}${style.letter}"
                    )
                }

                Text(
                    text = style.name,
                    fontWeight = FontWeight.Bold
                )
            },
            content = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Range("Original Gravity", style.minOG, style.maxOG)
                    Range("Final Gravity", style.minFG, style.maxFG)
                    Range("Bitterness (IBU)", style.minIBU, style.maxIBU)
                    Row {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Colour (SRM)",
                                fontWeight = FontWeight.Bold
                            )
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier
                                    .padding(bottom = 4.dp)
                                    .fillMaxWidth()
                            ){
                                Text(text = floor(style.minSRM.toDouble()).toInt().toString())
                                Text(text = "-")
                                Text(text = ceil(style.maxSRM.toDouble()).toInt().toString())
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                Recipe.srmToColour(floor(style.minSRM.toDouble()).toInt()),
                                                Recipe.srmToColour(ceil(style.maxSRM.toDouble()).toInt())
                                            )
                                        )
                                    )
                            )
                        }
                    }
                }
            }
        )
    }

    @Composable
    fun ShortRecipeCard(
        recipe: Recipe,
        viewRecipe: (recipe: Recipe) -> Unit
    ){
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth()
                .clickable {
                    viewRecipe(recipe)
                }
        ) {
            Column() {
                Text(
                    text = recipe.name,
                    fontWeight = FontWeight.Bold
                )
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(recipe.style.name, style = MaterialTheme.typography.body2)
                }
            }

            Column(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
            ) {
                Row {
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier
                            .padding(end = 4.dp)
                    ) {
                        Text(
                            text = "${DecimalFormat("#0.0").format(recipe.abv)}%"
                        )
                        Text(
                            text = "IBU: ${recipe.ibu}"
                        )
                    }
                    Column() {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(8.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(
                                    recipe.colour()
                                )
                        )
                    }
                }
            }
        }
    }
}