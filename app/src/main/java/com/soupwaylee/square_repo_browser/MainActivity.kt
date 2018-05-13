package com.soupwaylee.square_repo_browser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle


class MainActivity : AppCompatActivity(), RepoRecyclerViewFragment.OnRepoSelected, VolleyRequestCallback<ArrayList<Repo>> {
    private lateinit var networkFragment: NetworkFragment
    private var savedInstanceState: Bundle? = null
    private var repoList : ArrayList<Repo>? = null
    private var isDownloading : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.savedInstanceState = savedInstanceState
        setContentView(R.layout.activity_main)

        networkFragment = NetworkFragment.getInstance(
                supportFragmentManager,
                "https://api.github.com/orgs/square/repos",
                this
        )

        startDownload() // todo is this the right position?
    }

    override fun onRepoSelected(repo: Repo) {
//        val detailFragment =
//                RepoDetailFragment.newInstance(repo)
//
//        supportFragmentManager.beginTransaction()
//                .replace(R.id.root_layout, detailFragment, "repoDetails")
//                .addToBackStack(null)
//                .commit()
    }

    private fun startDownload() {
        if (!isDownloading && networkFragment != null) {
            networkFragment.startDownload()
            isDownloading = true
        }
    }

    override fun updateFromDownload(result: ArrayList<Repo>?) {
        repoList = result

        // if it's finished
        if (!isDownloading) {
            repoList ?: if (savedInstanceState == null) {
                supportFragmentManager
                        .beginTransaction()
                        .add(R.id.root_layout, RepoRecyclerViewFragment.newInstance(repoList!!), "repoRecycler")
                        .commit()
            }
        }
    }

    override fun finishDownloading() {
        isDownloading = false
        networkFragment.cancelDownload()
    }

    companion object {
        val TAG = "MainActivity"
    }
}
