package com.example.shoresafe

import android.accounts.NetworkErrorException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoresafe.data.PlaceSearchRepository
import com.example.weathersamachar.data.model.PlacesSearchResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val sd = "8669cac44e7143f4bb1c64abe0e480e9"
@HiltViewModel
class PlacesSearchViewModel @Inject constructor(
    private val placeSearchRepository: PlaceSearchRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(PlaceSearchUiState())
    val uiState = _uiState.asStateFlow()

    fun searchPlace(name: String) {
        viewModelScope.launch {
            try {
                _uiState.update { state->
                    state.copy(
                        response = placeSearchRepository.findPlace(name = name, apiKey = sd),
                        isError = false
                    )
                }
            } catch (e: NetworkErrorException) {
                _uiState.update {
                    it.copy(
                        isError = true,
                        error = e.message
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isError = true,
                        error = e.message
                    )
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            try {
                _uiState.update { state ->
                    state.copy(
                        response = placeSearchRepository.findPlace(
                            name = "Hyderabad",
                            apiKey = sd
                        ),
                        isError = false
                    )
                }
            } catch(e: Exception) {
                _uiState.update {
                    it.copy(
                        isError = true,
                        error = e.message ?: "Something went wrong"
                    )
                }
            }
        }
    }
}

data class PlaceSearchUiState(
    val response: PlacesSearchResponse? = null,
    val isError: Boolean = false,
    val error: String? = "Something went wrong!"
)