package io.brewdict.application.android

import android.util.Log
import io.brewdict.application.apis.brewdict.models.Fermentation
import kotlinx.coroutines.runBlocking

class Brewdict {
    companion object {
        init {
            System.loadLibrary("brewdict")
        }
    }

    fun predictFermentationDuration(f: Fermentation): Float {

        try {
            return predict(
                og = f.og,
                startTemp = f.startTemp,
                time = f.fermenetationTime,
                sg = f.sg,
                currentTemp = f.currentTemp,
                meanTemp = f.meanTemp
            )
        } catch (e: Exception){
          Log.e(e.toString(), e.stackTraceToString())
        }
        return 0f
    }

    //'OG'  'startTemp'  'time'  'SG'  'currentTemp'  'meanTemp'
    private external fun predict(og: Float, startTemp: Float, time: Float, sg: Float, currentTemp: Float, meanTemp: Float): Float
}