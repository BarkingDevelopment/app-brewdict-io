package io.brewdict.application.apis.brewdict.models

import android.graphics.Color
import io.brewdict.application.api_consumption.models.User
import io.brewdict.application.api_consumption.models.Model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recipe (
    @SerialName("id") val id: Int,
    @SerialName("owner") val owner: User,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("inspiration") val inspiration: Recipe? = null,
    @SerialName("style") val style: Style,
    @SerialName("abv") val abv: Float,
    @SerialName("ibu") val ibu: Int,
    @SerialName("srm") val srm: Int,
): Model {

    /**
     * Converts the SRM value of a beer recipe to a colour integer.
     *
     * Source: https://www.barleydogbrewery.com/xml/colors.xml
     */
    fun colour() : Int {
        when(srm){
            1 -> return Color.rgb(243, 249, 147)
            2 -> return Color.rgb(248, 247, 83)
            3 -> return Color.rgb(246, 245, 19)
            4 -> return Color.rgb(236, 230, 26)
            5 -> return Color.rgb(224, 204, 27)
            6 -> return Color.rgb(213, 188, 38)
            7 -> return Color.rgb(201, 166, 50)
            8 -> return Color.rgb(191, 146,59)
            9 -> return Color.rgb(190, 140, 58)
            10 -> return Color.rgb(191, 129, 58)
            11 -> return Color.rgb(190, 124, 55)
            12 -> return Color.rgb(191, 113, 56)
            13 -> return Color.rgb(188, 103, 51)
            14 -> return Color.rgb(178, 96, 51)
            15 -> return Color.rgb(165, 89, 54)
            16 -> return Color.rgb(152, 83, 54)
            17 -> return Color.rgb(141, 76, 50)
            18 -> return Color.rgb(124, 69, 45)
            19 -> return Color.rgb(107, 58, 30)
            20 -> return Color.rgb(93, 52, 26)
            21 -> return Color.rgb(78, 42, 12)
            22 -> return Color.rgb(74, 39, 39)
            23 -> return Color.rgb(54, 21, 27)
            24 -> return Color.rgb(38, 23, 22)
            25 -> return Color.rgb(38, 23, 22)
            26, 27 -> return Color.rgb(25, 16, 15)
            28 -> return Color.rgb(18, 13,12)
            29, 30 -> return Color.rgb(16, 11, 10)
            31 -> return Color.rgb(14, 9, 8)
            32 -> return Color.rgb(15, 11, 8 )
            33 -> return Color.rgb(12, 9, 7)
            34, 35 -> return Color.rgb(8, 7, 7)
            36 -> return Color.rgb(7, 6, 6)
            37, 38 -> return Color.rgb(4, 5, 4)
            39, 40 -> return Color.rgb(3,4,3)
            else -> return Color.rgb(3,4,3)
        }
    }
}
