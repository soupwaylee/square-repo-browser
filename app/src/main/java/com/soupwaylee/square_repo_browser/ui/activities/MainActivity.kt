package com.soupwaylee.square_repo_browser.ui.activities

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.soupwaylee.square_repo_browser.*
import com.soupwaylee.square_repo_browser.data.*
import com.soupwaylee.square_repo_browser.ui.fragments.RepoDetailFragment
import com.soupwaylee.square_repo_browser.ui.fragments.RepoListFragment
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity(), RepoListFragment.OnRepoSelected {
    private var savedInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.savedInstanceState = savedInstanceState
        setContentView(R.layout.activity_main)

        if (isNetworkConnected()) {
            doAsync {
                val result = Request().requestRepos()
                uiThread {
                    val listFragment = RepoListFragment.newInstance(RepoResult(result))
                    supportFragmentManager.beginTransaction()
                            .add(R.id.root_layout, listFragment, "repoRecycler")
                            .commit()
                }
            }
        } else {
            AlertDialog.Builder(this).setTitle("No Internet Connection")
                    .setMessage("Please check your internet connection and try again!")
                    .setPositiveButton(android.R.string.ok) { _, _ -> }
                    .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
    }

    override fun onRepoSelected(item: Item) {
        val id = item.name

        if (isNetworkConnected()) {
            doAsync {
                val result = Request().requestStargazers(id.orEmpty())
                uiThread {
                    val detailsFragment = RepoDetailFragment.newInstance(StargazerResult(result))
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.root_layout, detailsFragment, "repoDetails")
                            .addToBackStack(null)
                            .commit()
                }
            }
        } else {
            AlertDialog.Builder(this).setTitle("No Internet Connection")
                    .setMessage("Please check your internet connection and try again!")
                    .setPositiveButton(android.R.string.ok) { _, _ -> }
                    .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
