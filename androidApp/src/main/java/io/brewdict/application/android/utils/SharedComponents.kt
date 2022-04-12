package io.brewdict.application.android.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.brewdict.application.android.R

object SharedComponents {
    @Composable
    fun <T : MultiToggleButtonEnum> MultiToggleButton(options: Array<T>, default: T, onSelectionChange: (T) -> Unit) {
        var selectedOption by remember { mutableStateOf(default) }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            options.forEach { state ->
                Column(
                    modifier = Modifier
                        .padding(
                            end = 8.dp,
                        ),
                ) {
                    Text(
                        text = state.string,
                        color = Color.White,
                        modifier = Modifier
                            .clip(
                                shape = RoundedCornerShape(
                                    size = 12.dp,
                                ),
                            )
                            .clickable {
                                selectedOption = state
                                onSelectionChange(state)
                            }
                            .background(
                                // FIXME Replace colours.
                                if (state == selectedOption) {
                                    Color.Magenta
                                } else {
                                    Color.LightGray
                                }
                            )
                            .padding(
                                vertical = 16.dp,
                                horizontal = 16.dp,
                            ),
                    )
                }
            }
        }
    }

    @ExperimentalMaterialApi
    @Composable
    fun Accordion(header: @Composable() () -> Unit,
                  content: @Composable() () -> Unit
    ){
        var expanded by remember { mutableStateOf(false) }

        val rotateState = animateFloatAsState(
            targetValue = if (expanded) 180F else 0F,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Card(onClick = { expanded = !expanded }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    header()

                    Icon(
                        painter =  painterResource(R.drawable.ic_baseline_arrow_drop_down_24),
                        contentDescription = null,
                        modifier = Modifier
                            .rotate(rotateState.value)
                    )
                }
            }
            Divider()
            AnimatedVisibility(
                visible = expanded,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .padding(16.dp)
                ) {
                    content()
                }
            }
        }
    }

    @Composable
    fun Range(title: String, minVal: Float, maxVal: Float){
        Row {
            Text(
                text = title,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(text = minVal.toString())
            Text(text = "-")
            Text(text = maxVal.toString())
        }
    }
}