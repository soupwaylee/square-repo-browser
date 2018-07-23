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
import com.soupwaylee.square_repo_browser.data.Item
import com.soupwaylee.square_repo_browser.data.RepoResult
import kotlinx.android.synthetic.main.recycler_item_repo.view.*


class RepoListFragment : Fragment() {

    private lateinit var listener: OnRepoSelected

    companion object {
        private lateinit var repoList: RepoResult
        private val TAG = "RepoRecyclerViewAdapter"

        fun newInstance(repoList: RepoResult): RepoListFragment {
            this.repoList = repoList
            return RepoListFragment()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is OnRepoSelected) {
            listener = context
        } else {
            throw ClassCastException(context.toString() + " must implement OnRepoSelected")
        }
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView: View = inflater!!.inflate(R.layout.fragment_repo_recycler_view, container,
                false).apply { tag = TAG }
        val activity = activity
        val recyclerView = rootView.findViewById(R.id.repo_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = RepoRecyclerViewAdapter(activity, repoList)
        return rootView
    }


    inner class RepoRecyclerViewAdapter(context: Context, private val repoList: RepoResult) : RecyclerView.Adapter<RepoRecyclerViewAdapter.ViewHolder>() {
        private val layoutInflater: LayoutInflater

        init {
            layoutInflater = LayoutInflater.from(context)
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            val recyclerItemRepoBinding = layoutInflater.inflate(R.layout.recycler_item_repo, viewGroup, false)
            return ViewHolder(recyclerItemRepoBinding)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            viewHolder.bindRepo(repoList.items[position])
            viewHolder.itemView.setOnClickListener { listener.onRepoSelected(repoList.items[position]) }
        }

        override fun getItemCount() = repoList.items.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            fun bindRepo(repo: Item) {
                with(repo) {
                    itemView.repoTitleTextView.text = repo.name.orEmpty()
                    itemView.stargazersTextView.text = repo.stargazers_count.toString()
                }
            }
        }
    }

    interface OnRepoSelected {
        fun onRepoSelected(item: Item)
    }
}
