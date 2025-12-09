package com.loc.githubprejects.usecase

import com.loc.githubprejects.model.RepoItem
import com.loc.githubprejects.repository.GithubRepository
import javax.inject.Inject

class SearchFilteredReposUseCase @Inject constructor(
    private val githubRepository: GithubRepository
) {
    suspend operator fun invoke(query: String): List<RepoItem> {

        val response = githubRepository.getSearch(query)

        val filteredList = response.filter { repo ->
           // repo.name.contains("Android", ignoreCase = true)
             repo.stars > 50
        }


        return filteredList
    }
}