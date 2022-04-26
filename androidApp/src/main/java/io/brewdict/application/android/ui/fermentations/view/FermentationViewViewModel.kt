package io.brewdict.application.android.ui.fermentations.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.brewdict.application.android.R
import io.brewdict.application.android.ui.recipes.edit.RecipeSaveResult
import io.brewdict.application.android.ui.recipes.view.FermentationCreateResult
import io.brewdict.application.api_consumption.Result
import io.brewdict.application.apis.brewdict.BrewdictAPI
import io.brewdict.application.apis.brewdict.endpoints.FermentationEndpoint
import io.brewdict.application.apis.brewdict.models.Fermentation
import io.brewdict.application.apis.brewdict.models.Recipe

class FermentationViewViewModel : ViewModel() {
    private val _fermentationStartResult = MutableLiveData<FermentationStartResult>()
    val fermentationStartResult: LiveData<FermentationStartResult> = _fermentationStartResult

    private val _fermentationCompleteResult = MutableLiveData<FermentationCompleteResult>()
    val fermentationCompleteResult: LiveData<FermentationCompleteResult> = _fermentationCompleteResult

    private val _fermentationCreateResult = MutableLiveData<FermentationCreateResult>()
    val fermentationCreateResult: LiveData<FermentationCreateResult> = _fermentationCreateResult

    lateinit var fermentation: Fermentation

    fun getFermentation(fermentation: Fermentation) {
        val result = BrewdictAPI.endpoints["fermentations"]!!.get(fermentation.id!!)

        this.fermentation = if (result is Result.Success)
            result.data as Fermentation
        else
            fermentation
    }

    fun startFermentation(og:  Float?){
        if (og == null){
            _fermentationStartResult.value = FermentationStartResult(error = R.string.login_failed)
            return
        }

        val endpoint = BrewdictAPI.endpoints["fermentations"]!! as FermentationEndpoint

        val result = endpoint.start(fermentation.id!!, og)

        if (result is Result.Success) _fermentationStartResult.value = FermentationStartResult(success = result.data)
        else _fermentationStartResult.value = FermentationStartResult(error = R.string.login_failed)
    }

    fun completeFermentation() {
        val endpoint = BrewdictAPI.endpoints["fermentations"]!! as FermentationEndpoint

        val result = endpoint.complete(fermentation.id!!)

        if (result is Result.Success) _fermentationCompleteResult.value = FermentationCompleteResult(success = result.data)
        else _fermentationCompleteResult.value = FermentationCompleteResult(error = R.string.login_failed)
    }

    fun createFermentation(recipe: Recipe){
        val resource = BrewdictAPI.endpoints["fermentations"]!! as FermentationEndpoint

        val result = resource.create(
            Fermentation(
                id = null,
                recipe = recipe,
                brewer = BrewdictAPI.loggedInUser!!.user,
                isComplete = false
            )
        )

        if (result is Result.Success) _fermentationCreateResult.value = FermentationCreateResult(success = result.data)
        else _fermentationCreateResult.value = FermentationCreateResult(error = R.string.login_failed)
    }
}