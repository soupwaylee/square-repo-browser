package com.soupwaylee.square_repo_browser


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import java.io.Serializable


/**
 * A simple [Fragment] subclass.
 *
 */
class RepoDetailFragment : Fragment() {
    private lateinit var stargazerList : ArrayList<Stargazer>
    var requestQueue: RequestQueue? = null

// todo pass repoID property

    companion object {
        private val TAG = "RepoDetailsRecyclerView"

        fun newInstance(repo: Repo): RepoDetailFragment {
            val args = Bundle()
            args.putSerializable("repo", repo as Serializable)
            val fragment = RepoDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        requestQueue = Volley.newRequestQueue(activity)

//        stargazerList = getStargazerList()
    }

    private fun getStargazerList(repoID: String) : ArrayList<Repo> {
        val result = ArrayList<Repo>()

        val arrReq = JsonArrayRequest(Request.Method.GET, "https://api.github.com/repos/square/$repoID/stargazers", null,
                Response.Listener { response ->
                    if (response.length() > 0) {
                        // The user does have repos, so let's loop through them all.
                        for (i in 0 until response.length()) {
                            try {
                                // For each repo, add a new line to our repo list.
                                val jsonObj = response.getJSONObject(i)
                                val repoId = jsonObj.get("id").toString()
                                val repoName = jsonObj.get("name").toString()
                                val repoStargazersCount = jsonObj.get("stargazers_count").toString()
                                result.add(Repo(id=repoId, name=repoName, stars=repoStargazersCount))
                            } catch (e: JSONException) {
                                Log.e("${MainActivity.TAG} Volley", "Invalid JSON Object.")
                            }
                        }
                    } else {
                        Toast.makeText(activity, "No repos found.", Toast.LENGTH_LONG).show()
                    }
                },
                Response.ErrorListener { error ->
                    Log.i("${MainActivity.TAG} RespErr", error.message)
                    Toast.makeText(
                            activity,
                            "Unsuccessful request to GitHub endpoint.",
                            Toast.LENGTH_LONG).show()
                }
        )
        requestQueue?.add(arrReq)

        return result
    }

}
