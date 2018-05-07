package com.soupwaylee.square_repo_browser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle


class MainActivity : AppCompatActivity(), RepoRecyclerViewFragment.OnRepoSelected {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.root_layout, RepoRecyclerViewFragment.newInstance(), "repoRecycler")
                    .commit()
        }
    }

    override fun onRepoSelected(repo: Repo) {
        val detailFragment =
                RepoDetailFragment.newInstance(repo)
        supportFragmentManager.beginTransaction()
                .replace(R.id.root_layout, detailFragment, "repoDetails")
                .addToBackStack(null)
                .commit()
    }

    companion object {
        val TAG = "MainActivity"
    }
}
