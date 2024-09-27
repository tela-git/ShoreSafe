package com.example.shoresafe.data.util

import com.example.shoresafe.data.model.beachweather.Current
import com.example.shoresafe.data.model.beachweather.Daily
import com.example.shoresafe.data.model.beachweather.MarineWeather

fun checkBeachSafety(beachWeatherReport: MarineWeather): Suitability {
    val currentParameters = beachWeatherReport.current
    val dailyParameters = beachWeatherReport.daily

    return checkCurrentConditions(currentParameters)
}

fun checkCurrentConditions(
    currentParameters: Current
): Suitability {
    return when {
        checkWaveHeightCondition(currentParameters.wave_height) == Suitability.NotSafe
                || checkSwellWaveHeightCondition(currentParameters.swell_wave_height) == Suitability.NotSafe -> {
                    Suitability.NotSafe
                }
        checkSwellWaveHeightCondition(currentParameters.swell_wave_height) == Suitability.NotForBeginners
        -> {
            Suitability.NotSafeForBeginnersAndChildren
        }
        checkWaveHeightCondition(currentParameters.wave_height) == Suitability.NotForChildren
                || checkSwellWaveHeightCondition(currentParameters.swell_wave_height) == Suitability.NotForChildren -> {
                    Suitability.NotForChildren
                }
        else -> { Suitability.Safe }
    }
}

fun checkWaveHeightCondition(
    waveHeight: Double
) :Suitability {
    return when (waveHeight) {
        in (0.0 .. 1.3) -> {
            Suitability.Safe
        }
        in (1.3 .. 1.8) -> {
            Suitability.NotForChildren
        }
        else -> {
            Suitability.NotSafe
        }
    }
}

fun checkSwellWaveHeightCondition(
    swellWaveHeight: Double
) : Suitability {
    return when(swellWaveHeight) {
        in (0.0 .. 0.8) ->  Suitability.Safe
        in (0.8 .. 1.4) ->  Suitability.NotForChildren
        in (1.4 .. 2.4) ->  Suitability.NotForBeginners
        else -> Suitability.NotSafe
    }
}

fun checkDailyConditions(dailyParameters: Daily) {

}

sealed class Suitability() {
    data object NotSafe : Suitability()
    data object NotForBeginners: Suitability()
    data object NotForChildren: Suitability()
    data object NotSafeForBeginnersAndChildren: Suitability()
    data object Safe: Suitability()
}
