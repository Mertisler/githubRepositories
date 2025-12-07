package com.loc.githubprejects.model

import com.google.gson.annotations.SerializedName

data class GithubSearchResponse(
    val items: List<RepoItem>
)

data class RepoItem(
    val id: Int,
    val name: String,
    val description: String?,
    @SerializedName("stargazers_count") val stars: Int
)
