package com.soupwaylee.square_repo_browser.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.soupwaylee.square_repo_browser.R
import com.soupwaylee.square_repo_browser.ui.fragments.RepoDetailFragment

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val frag = supportFragmentManager.findFragmentById(R.id.detail_fragment) as RepoDetailFragment

    }
}
