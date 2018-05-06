package com.soupwaylee.square_repo_browser


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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

import com.soupwaylee.square_repo_browser.databinding.RecyclerItemRepoBinding

/**
 * A simple [Fragment] subclass.
 *
 */
class RepoRecyclerViewFragment : Fragment() {
    private lateinit var layoutManager: RecyclerView.LayoutManager

    private var SQUARE_REPOS_URL = "https://api.github.com/orgs/square/repos"
    private lateinit var repoList : ArrayList<Repo>

    private lateinit var listener : OnRepoSelected

    var requestQueue: RequestQueue? = null

    companion object {
        private val TAG = "RepoRecyclerViewAdapter"

        fun newInstance(): RepoRecyclerViewFragment {
            return RepoRecyclerViewFragment()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is OnRepoSelected) {
            listener = context
        } else {
            throw ClassCastException(context.toString() + " must implement OnRageComicSelected")
        }

        requestQueue = Volley.newRequestQueue(activity)

        repoList = ArrayList()

        // get repo list here instead of within main activity
        getRepoList()
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_repo_recycler_view, container,
                false).apply { tag = TAG}
        val activity = activity
        val recyclerView = rootView.findViewById(R.id.repoRecyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = RepoRecyclerViewAdapter(activity, repoList)
        return rootView
    }

    private fun getRepoList() {
        val arrReq = JsonArrayRequest(Request.Method.GET, SQUARE_REPOS_URL, null,
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
                                repoList.add(Repo(id=repoId, name=repoName, stars=repoStargazersCount))
                            } catch (e: JSONException) {
                                Log.e("${MainActivity.TAG} Volley", "Invalid JSON Object.")
                            }
                        }
                        Toast.makeText(activity, "Gotten repos.", Toast.LENGTH_LONG).show()
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
    }

    internal inner class RepoRecyclerViewAdapter(context: Context, private val repoArrayList: ArrayList<Repo>) :
            RecyclerView.Adapter<ViewHolder>() {

        private val layoutInflater: LayoutInflater

        init {
            layoutInflater = LayoutInflater.from(context)
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            val recyclerItemRepoBinding = RecyclerItemRepoBinding.inflate(
                    layoutInflater, viewGroup, false)
            return ViewHolder(recyclerItemRepoBinding.root, recyclerItemRepoBinding)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            Log.d(TAG, "Element $position set.")
            val repo = Repo(repoArrayList[position].id, repoArrayList[position].name, repoArrayList[position].stars)
            viewHolder.setData(repo)
            viewHolder.itemView.setOnClickListener{ listener.onRepoSelected(repo) }
        }


        override fun getItemCount() = repoArrayList.size
    }


    internal inner class ViewHolder constructor(itemView: View,
                                                val recyclerItemRepoBinding: RecyclerItemRepoBinding) :
            RecyclerView.ViewHolder(itemView) {

        fun setData (repo: Repo) {
            recyclerItemRepoBinding.repo = repo
        }
    }

    interface OnRepoSelected {
        fun onRepoSelected(repo: Repo)
    }
}
