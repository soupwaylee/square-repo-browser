package com.soupwaylee.square_repo_browser.data

data class RepoResult(val items: List<Item>)

data class StargazerResult(val owners: List<Owner>)

data class Item(
        val id: Long?,
        val name: String?,
        val stargazers_count: Long?
)

data class Owner(
        val login: String?,
        val id: Long?,
        val avatar_url: String?
)