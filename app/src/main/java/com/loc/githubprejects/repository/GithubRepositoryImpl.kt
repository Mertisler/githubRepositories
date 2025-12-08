package com.loc.githubprejects.repository

import com.loc.githubprejects.GithubApiService
import com.loc.githubprejects.model.RepoItem
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val api : GithubApiService
): GithubRepository {
    override suspend fun getSearch(search: String): List<RepoItem> {
        val response=api.searchRepos(search)
        return response.items
    }
}