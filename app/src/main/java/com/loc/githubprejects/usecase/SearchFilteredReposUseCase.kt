package com.loc.githubprejects.usecase

import com.loc.githubprejects.model.RepoItem
import com.loc.githubprejects.repository.GithubRepository

class SearchFilteredReposUseCase(
    private val githubRepository: GithubRepository

) {
    suspend operator fun invoke(query: String): List<RepoItem>{
        return githubRepository.getSearch(query)

    }
}