package com.example.shoresafe

import android.accounts.NetworkErrorException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoresafe.data.BeachSearchRepository
import com.example.shoresafe.data.model.BeachSearchResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeachSearchViewModel @Inject constructor(
    private val beachSearchRepository: BeachSearchRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(BeachSearchUiState())
    val uiState = _uiState.asStateFlow()

    fun searchBeach(query: String) {
        viewModelScope.launch {
            try {
                val beachList = beachSearchRepository.queryBeaches(query)
                _uiState.update { state->
                    state.copy(
                        response = BeachSearchResponse(beachList),
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
                print(e)
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
                val beachesList = beachSearchRepository.listAllBeaches()
                _uiState.update { state->
                    state.copy(
                        response = BeachSearchResponse(beaches = beachesList),
                        isError = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update { state->
                    state.copy(
                        isError = true,
                        error = e.message
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