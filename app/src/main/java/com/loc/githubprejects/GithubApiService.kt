package com.loc.githubprejects

import com.loc.githubprejects.model.GithubSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService{
    @GET("search/repositories")
    suspend fun searchRepos(
        @Query("q") query: String,
    ): GithubSearchResponse
}