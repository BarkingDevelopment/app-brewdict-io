package io.brewdict.application.apis.brewdict.models

import androidx.compose.ui.graphics.Color
import io.brewdict.application.api_consumption.models.User
import io.brewdict.application.api_consumption.models.Model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.lang.Math.round

@Serializable
data class Recipe (
    @SerialName("id") val id: Int?,
    @SerialName("owner") val owner: User,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("inspiration") val inspiration: Recipe? = null,
    @SerialName("style") val style: Style,
    @SerialName("og") val og: Float,
    @SerialName("fg") val fg: Float,
    @SerialName("ibu") val ibu: Int,
    @SerialName("srm") val srm: Int,
): Model {
    companion object {
        /**
         * Converts the SRM value of a beer recipe to a colour integer.
         *
         * Source: https://www.barleydogbrewery.com/xml/colors.xml
         */

        fun srmToColour(int: Int) : Color {
            when(int){
                1 -> return Color(243, 249, 147)
                2 -> return Color(248, 247, 83)
                3 -> return Color(246, 245, 19)
                4 -> return Color(236, 230, 26)
                5 -> return Color(224, 204, 27)
                6 -> return Color(213, 188, 38)
                7 -> return Color(201, 166, 50)
                8 -> return Color(191, 146,59)
                9 -> return Color(190, 140, 58)
                10 -> return Color(191, 129, 58)
                11 -> return Color(190, 124, 55)
                12 -> return Color(191, 113, 56)
                13 -> return Color(188, 103, 51)
                14 -> return Color(178, 96, 51)
                15 -> return Color(165, 89, 54)
                16 -> return Color(152, 83, 54)
                17 -> return Color(141, 76, 50)
                18 -> return Color(124, 69, 45)
                19 -> return Color(107, 58, 30)
                20 -> return Color(93, 52, 26)
                21 -> return Color(78, 42, 12)
                22 -> return Color(74, 39, 39)
                23 -> return Color(54, 21, 27)
                24 -> return Color(38, 23, 22)
                25 -> return Color(38, 23, 22)
                26, 27 -> return Color(25, 16, 15)
                28 -> return Color(18, 13,12)
                29, 30 -> return Color(16, 11, 10)
                31 -> return Color(14, 9, 8)
                32 -> return Color(15, 11, 8 )
                33 -> return Color(12, 9, 7)
                34, 35 -> return Color(8, 7, 7)
                36 -> return Color(7, 6, 6)
                37, 38 -> return Color(4, 5, 4)
                39, 40 -> return Color(3,4,3)
                else -> return Color(3,4,3)
            }
        }

        fun colourGradient(minVal: Int, maxVal: Int): List<Color>{
          return (minVal .. maxVal).map { srmToColour(it) }
        }
    }

    val abv : Float
        get() =  (og - fg) * 131.25f

    fun colour() : Color {
        return srmToColour(srm)
    }
}
