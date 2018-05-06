package com.soupwaylee.square_repo_browser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            // grab FragmentManager by referencing supportFragmentManager
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.root_layout, RepoRecyclerViewFragment.newInstance())
                    .commit() // execute the transaction
        }
    }

    companion object {
        val TAG = "MainActivity"
    }
}
