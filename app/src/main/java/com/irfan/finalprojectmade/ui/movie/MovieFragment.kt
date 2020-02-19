package com.irfan.finalprojectmade.ui.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.irfan.finalprojectmade.R
import com.irfan.finalprojectmade.adapter.MovieAdapter
import com.irfan.finalprojectmade.model.Movie
import com.irfan.finalprojectmade.network.ApiListener
import com.irfan.finalprojectmade.ui.detail.DetailActivity
import com.irfan.finalprojectmade.util.EndlessRecyclerOnScrollListener
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.shimmer_layout.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MovieFragment : Fragment(), ApiListener {

    private lateinit var homeViewModel: MovieViewModel
    private lateinit var adapterMovie: MovieAdapter
    private val data = arrayListOf<Movie>()
    private lateinit var scrollListener: EndlessRecyclerOnScrollListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterMovie = MovieAdapter(context!!, data)
        adapterMovie.setOnItemClickListener(object : MovieAdapter.OnItemClickListener{
            override fun onItemClicked(data: Movie) {
                activity?.toast(data.title)
                activity?.startActivity<DetailActivity>(
                    DetailActivity.DATA_INTENT_MOVIE to data,
                    DetailActivity.FLAG to "movie"
                )
                activity?.finish()
            }
        })
        val layoutManager = LinearLayoutManager(activity)
//        val layoutManager = GridLayoutManager(activity, 2)
        scrollListener = object : EndlessRecyclerOnScrollListener(layoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                activity?.toast(page.toString())
                Log.e("datanya", page.toString())
            }
        }
        rv_movie.addOnScrollListener(scrollListener)
        rv_movie.layoutManager = layoutManager
        rv_movie.adapter = adapterMovie
        if (activity != null && isAdded){
            initObserver()
            fetchData()
        }
    }

    private fun initObserver() {
        homeViewModel.apiListener = this
        homeViewModel.getData().observe(this, Observer { datas ->
            showData(datas)
        })
    }

    private fun showData(datas: List<Movie>) {
        data.addAll(datas)
        adapterMovie.notifyDataSetChanged()
    }

    private fun fetchData() {
        startShimmer()
        homeViewModel.fetchData()
    }

    override fun onSucess() {
        stopShimmer()
    }

    override fun onError(message: String?) {
        if (activity != null && isAdded){
            stopShimmer()
        }
    }

    private fun startShimmer() {
        rv_movie.visibility = View.INVISIBLE
        shimmer_layout.visibility = View.VISIBLE
        shimmer_layout.startShimmer()
    }

    private fun stopShimmer() {
        shimmer_layout.stopShimmer()
        shimmer_layout.visibility = View.GONE
        rv_movie.visibility = View.VISIBLE
    }
}