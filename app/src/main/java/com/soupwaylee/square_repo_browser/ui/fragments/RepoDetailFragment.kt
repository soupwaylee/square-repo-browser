package com.soupwaylee.square_repo_browser.ui.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.soupwaylee.square_repo_browser.R
import com.soupwaylee.square_repo_browser.data.Owner
import com.soupwaylee.square_repo_browser.data.StargazerResult
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_item_stargazer.view.*


class RepoDetailFragment : Fragment() {

    companion object {
        private lateinit var stargazerList: StargazerResult
        private val TAG = "RepoDetailsRecyclerView"

        fun newInstance(stargazerList: StargazerResult): RepoDetailFragment {
            this.stargazerList = stargazerList
            return RepoDetailFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater!!.inflate(R.layout.fragment_repo_detail, container, false).apply { tag = RepoDetailFragment.TAG }
        val activity = activity
        val recyclerView = rootView.findViewById(R.id.repo_detail_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = RepoDetailAdapter(activity, stargazerList)
        return rootView
    }


    inner class RepoDetailAdapter(context: Context, private val stargazerList: StargazerResult) : RecyclerView.Adapter<RepoDetailAdapter.ViewHolder>() {
        private val layoutInflater: LayoutInflater

        init {
            layoutInflater = LayoutInflater.from(context)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val recyclerItemRepoBinding = layoutInflater.inflate(R.layout.recycler_item_stargazer, parent, false)
            return ViewHolder(recyclerItemRepoBinding)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            viewHolder.bindRepo(stargazerList.owners[position])
        }

        override fun getItemCount() = stargazerList.owners.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            fun bindRepo(owner: Owner) {
                with(owner) {
                    Picasso.get().load(owner.avatar_url).into(itemView.stargazerAvatar)
                    itemView.stargazerLogin.text = owner.login.orEmpty()
                }
            }
        }
    }
}
