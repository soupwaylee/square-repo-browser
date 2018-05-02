package com.soupwaylee.square_repo_browser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException

class MainActivity : AppCompatActivity() {
    private val SQUARE_REPOS_URL = "https://api.github.com/orgs/square/repos"
    private val TAG = "MainActivity"
    private val repoList = ArrayList<Repo>()

    var requestQueue: RequestQueue? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestQueue = Volley.newRequestQueue(this)

//        if (savedInstanceState == null) {
//            supportFragmentManager
//                    .beginTransaction()
//                    .add(R.id.root_layout)
//        }
    }

    // Method to get json file
    fun getRepoData() {
//        val githubEndpoint = URL(SQUARE_REPOS_URL)
//        val connection = githubEndpoint.openConnection() as HttpsURLConnection
//
//        // todo should I add request headers?
//
//        return if (connection.responseCode == 200) {
//            val responseBody = connection.inputStream
//            val responseBodyReader = InputStreamReader(responseBody, "UTF-8")
//            JsonReader(responseBodyReader)
//        } else {
//            val msg = "Unsuccessful request to GitHub endpoint."
//            Log.i(TAG, msg)
//            Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show() // todo check why this works
//            null
//        }
        // todo check if method signature might have changed
        val arrReq = JsonArrayRequest(Request.Method.GET, SQUARE_REPOS_URL, null,
                Response.Listener { response ->
                    if (response.length() > 0) {
                        // The user does have repos, so let's loop through them all.
                        for (i in 0 until response.length()) {
                            try {
                                // For each repo, add a new line to our repo list.
                                val jsonObj = response.getJSONObject(i)
                                val repoName = jsonObj.get("name").toString()
                                val stargazersCount = jsonObj.get("stargazers_count").toString()
                                repoList.add(Repo(repoName, stargazersCount))
                            } catch (e: JSONException) {
                                Log.e("$TAG Volley", "Invalid JSON Object.")
                            }
                        }
                    } else {
                        Toast.makeText(applicationContext, "No repos found.", Toast.LENGTH_LONG).show()
                    }
                },
                Response.ErrorListener { error ->
                    Log.i("$TAG RespErr", error.message)
                    Toast.makeText(
                            applicationContext,
                            "Unsuccessful request to GitHub endpoint.",
                            Toast.LENGTH_LONG).show() // todo check why this works
                }
        )
        requestQueue?.add(arrReq)
    }
}
