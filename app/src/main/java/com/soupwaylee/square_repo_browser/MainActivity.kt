package com.soupwaylee.square_repo_browser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle


class MainActivity : AppCompatActivity() {

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

//    override fun OnRepoSelected(repo: Repo) {
//        val detailFragment =
//                RepoDetailFragment.newInstance(repo)
//        supportFragmentManager.beginTransaction()
//                .replace(R.id.root_layout, detailFragment, "repoDetails") // replace the fragment that is currently populating the container
//                .addToBackStack(null) // Fragment back stack = history (just like activities)
//                .commit()
//    }

    companion object {
        val TAG = "MainActivity"
    }
}
