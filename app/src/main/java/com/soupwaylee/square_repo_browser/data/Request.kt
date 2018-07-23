package com.soupwaylee.square_repo_browser.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URL

class Request {

    companion object {
        private val URL = "https://api.github.com/orgs/square/repos"
    }

    fun requestRepos(): List<Item> {
        val repoListJsonStr = URL(URL).readText()
        // define the type of the collection first.
        return Gson().fromJson(repoListJsonStr, object : TypeToken<List<Item>>() {}.type)
    }

    fun requestStargazers(id: String): List<Owner> {
        val stargazerListJsonStr = URL("https://api.github.com/repos/square/$id/stargazers").readText()
        return Gson().fromJson(stargazerListJsonStr, object : TypeToken<List<Owner>>() {}.type)
    }
}
