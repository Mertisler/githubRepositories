package com.loc.githubprejects.search

import com.loc.githubprejects.model.RepoItem

sealed interface SearchUiState{
     object Idle: SearchUiState
     object Loading: SearchUiState
     data class  Error (val message: String): SearchUiState
     data class Succes(val repos : List<RepoItem>): SearchUiState
}