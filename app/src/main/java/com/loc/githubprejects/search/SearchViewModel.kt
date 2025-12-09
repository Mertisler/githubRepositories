package com.loc.githubprejects.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.githubprejects.usecase.SearchFilteredReposUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchFilteredReposUseCase: SearchFilteredReposUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    fun search(query: String) {

        if (query.isBlank()) return

        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading
            try {
                val resultList = searchFilteredReposUseCase(query)

                if (resultList.isEmpty()) {
                    _uiState.value = SearchUiState.Error("Aradığınız kriterde repo bulunamadı.")
                } else {

                    _uiState.value = SearchUiState.Succes(resultList)
                }

            } catch (e: Exception) {

                _uiState.value = SearchUiState.Error("Bir hata oluştu: ${e.message}")
            }
        }
    }
}