package com.example.shoresafe

import android.accounts.NetworkErrorException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoresafe.data.model.BeachSearchResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val sd = ""
@HiltViewModel
class BeachSearchViewModel @Inject constructor(
): ViewModel() {
    private val _uiState = MutableStateFlow(BeachSearchUiState())
    val uiState = _uiState.asStateFlow()

    fun searchBeach(query: String) {
        viewModelScope.launch {
            try {
                _uiState.update { state->
                    state.copy(
                        response = null,
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
                        response = null,
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

data class BeachSearchUiState(
    val response: BeachSearchResponse? = null,
    val isError: Boolean = false,
    val error: String? = "Something went wrong!"
)