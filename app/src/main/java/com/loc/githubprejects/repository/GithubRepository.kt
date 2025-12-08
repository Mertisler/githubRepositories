package com.loc.githubprejects.repository

import com.loc.githubprejects.model.RepoItem

interface GithubRepository {
    suspend fun getSearch(search: String): List<RepoItem>
}