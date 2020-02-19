package com.irfan.finalprojectmade.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.irfan.finalprojectmade.R
import com.irfan.finalprojectmade.adapter.FavoriteAdapter
import com.irfan.finalprojectmade.model.Favorite
import com.irfan.finalprojectmade.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.shimmer_layout.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class FavoriteFragment : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter
    val data = arrayListOf<Favorite>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel::class.java)
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        fetchData()
    }

    private fun initObserver() {
        startShimmer()
        favoriteViewModel.getData().observe(this, Observer { listData ->
            setData(listData)
        })
    }

    private fun setData(listData: List<Favorite>) {
        rv_favorite.layoutManager = LinearLayoutManager(activity)
        data.addAll(listData)
        adapter = FavoriteAdapter(context!!, data)
        adapter.setOnItemClickListener(object : FavoriteAdapter.OnItemClickListener{
            override fun onItemClicked(favorite: Favorite) {
                activity?.toast(favorite.title!!)
                activity?.startActivity<DetailActivity>(
                    DetailActivity.DATA_INTENT_FAVORITE to favorite,
                    DetailActivity.FLAG to "favorite")
                activity?.finish()
            }
        })
        adapter.notifyDataSetChanged()
        rv_favorite.adapter = adapter
    }

    private fun fetchData() {
        favoriteViewModel.fetchData()
        stopShimmer()
    }

    private fun startShimmer() {
        rv_favorite.visibility = View.GONE
        shimmer_layout.visibility = View.VISIBLE
        shimmer_layout.startShimmer()
    }

    private fun stopShimmer() {
        rv_favorite.visibility = View.VISIBLE
        shimmer_layout.stopShimmer()
        shimmer_layout.visibility = View.GONE
    }

}