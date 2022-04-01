package io.brewdict.application.apis.brewdict.models

import io.brewdict.application.api_consumption.models.Model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Style(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("style_category") val category: StyleCategory,
    @SerialName("style_letter") val letter: Char,
    @SerialName("type") val type: String,
    @SerialName("og_min") val minOG: Float,
    @SerialName("og_max") val maxOG: Float,
    @SerialName("fg_min") val minFG: Float,
    @SerialName("fg_max")  val maxFG: Float,
    @SerialName("ibu_min")  val minIBU: Float,
    @SerialName("ibu_max") val maxIBU: Float,
    @SerialName("srm_min")  val minSRM: Float,
    @SerialName("srm_max")  val maxSRM: Float,
    @SerialName("notes")   val notes: String,
) : Model{

}
