package com.example.shoresafe

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoresafe.data.BeachWeatherRepository
import com.example.shoresafe.data.model.beachweather.MarineWeather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeachWeatherViewModel @Inject constructor(
    private val beachWeatherRepository: BeachWeatherRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(BeachWeatherUiState())
    val uiState = _uiState.asStateFlow()

    fun getBeachWeather(latitude: String, longitude: String) {
        viewModelScope.launch {
            try {
                val result = beachWeatherRepository.getBeachWeather(
                    latitude = latitude,
                    longitude = longitude
                )
                _uiState.update { state->
                    state.copy(
                        response = result,
                        isError = false,
                        isLoading = false
                    )
                }
            }catch (e:Exception) {
                _uiState.update { state->
                    state.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = e.message
                    )
                }
            }
        }
    }

    fun setToLoading() {
        _uiState.update {
            it.copy(
                response = null,
                isLoading = true,
                isError = false
            )
        }
    }

    init {
        viewModelScope.launch {
            try {
                val result = beachWeatherRepository.getBeachWeather(
                    latitude = "12.9885",
                    longitude = "80.2583",
                    pastDays = 1,
                    dailyParameters = "wave_height_max,wind_wave_height_max,swell_wave_height_max",
                    currentParameters = "wave_height,wind_wave_height,swell_wave_height,ocean_current_velocity"
                )
                _uiState.update { state->
                    state.copy(
                        isError = false,
                        response = result,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update { state->
                    state.copy(
                        isError = true,
                        errorMessage = e.message ?: "Something went wrong",
                        isLoading = false
                    )
                }
            }
        }
    }
}

data class BeachWeatherUiState(
    val response: MarineWeather? = null,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val isLoading: Boolean = true
)