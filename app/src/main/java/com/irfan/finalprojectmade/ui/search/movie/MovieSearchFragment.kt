package com.irfan.finalprojectmade.ui.search.movie

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.irfan.finalprojectmade.R
import com.irfan.finalprojectmade.adapter.MovieAdapter
import com.irfan.finalprojectmade.model.Movie
import com.irfan.finalprojectmade.network.ApiListener
import com.irfan.finalprojectmade.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.movie_search_fragment.*
import kotlinx.android.synthetic.main.shimmer_layout.*
import org.jetbrains.anko.startActivity

class MovieSearchFragment : Fragment(), ApiListener {

    companion object {
        private const val ARG_QUERY = "query"
        private lateinit var query: String

        fun newInstance(): MovieSearchFragment{
            val fragment = MovieSearchFragment()
//            val args = Bundle()
//            args.putString(ARG_QUERY, query)
//            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: MovieSearchViewModel
    private lateinit var adapter: MovieAdapter
    private lateinit var recyclerView: RecyclerView
    private var datas = arrayListOf<Movie>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MovieSearchViewModel::class.java)
//        query = arguments?.getString("query")!!
        initObserver()
        recyclerView = rv_movie_search
    }

    private fun initObserver() {
        viewModel.apiListener = this
        viewModel.fetchData().observe(this, Observer { listData ->
            showData(listData)
        })
    }

    fun changingData(queries: String){
        startShimmer()
        viewModel.getData(queries)
    }

    private fun callData() {
        startShimmer()
        Log.e("datanyapasfragment", query)
        viewModel.getData(query)
    }

    override fun onSucess() {
        stopShimmer()
    }

    override fun onError(message: String?) {
        stopShimmer()
    }

    private fun showData(listData: List<Movie>) {
        datas.clear()
        datas.addAll(listData)
        adapter = MovieAdapter(context!!, datas)
        adapter.setOnItemClickListener(object : MovieAdapter.OnItemClickListener{
            override fun onItemClicked(data: Movie) {
                activity?.startActivity<DetailActivity>(
                    DetailActivity.DATA_INTENT_SERIAL to data,
                    DetailActivity.FLAG to "movie"
                )
            }
        })
        adapter.notifyDataSetChanged()
        rv_movie_search.layoutManager = LinearLayoutManager(activity)
        rv_movie_search.adapter = adapter
    }

    private fun startShimmer() {
        shimmer_layout.visibility = View.VISIBLE
        rv_movie_search.visibility = View.INVISIBLE
        shimmer_layout.startShimmer()
    }

    private fun stopShimmer() {
        shimmer_layout.stopShimmer()
        shimmer_layout.visibility = View.GONE
        rv_movie_search.visibility = View.VISIBLE
    }
}
