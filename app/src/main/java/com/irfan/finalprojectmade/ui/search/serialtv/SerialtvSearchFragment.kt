package com.irfan.finalprojectmade.ui.search.serialtv

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.irfan.finalprojectmade.R
import com.irfan.finalprojectmade.adapter.SerialtvAdapter
import com.irfan.finalprojectmade.model.Serialtv
import com.irfan.finalprojectmade.network.ApiListener
import com.irfan.finalprojectmade.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.serialtv_search_fragment.*
import kotlinx.android.synthetic.main.shimmer_layout.*
import org.jetbrains.anko.startActivity

class SerialtvSearchFragment : Fragment(), ApiListener {

    companion object {
        fun newInstance(): SerialtvSearchFragment{
            val fragment = SerialtvSearchFragment()
            return fragment
        }
    }

    private lateinit var viewModel: SerialtvSearchViewModel
    private lateinit var adapter: SerialtvAdapter
    private lateinit var recyclerView: RecyclerView
    private var query: String? = null
    private var datas = arrayListOf<Serialtv>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.serialtv_search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SerialtvSearchViewModel::class.java)
//        query = arguments?.getString(ARG_QUERY)
        initObserver()
//        callData()
    }

    private fun initObserver() {
        viewModel.apiListener = this
        viewModel.fetchData().observe(this, Observer { listData ->
            showData(listData)
        })
    }

    fun changingData(queries: String){
//        startShimmer()
        viewModel.getData(queries)
    }

    private fun callData() {
        startShimmer()
        viewModel.getData(query!!)
    }

    private fun showData(listData: List<Serialtv>) {
        datas.clear()
        datas.addAll(listData)
        Log.e("datanya", listData.size.toString())
        adapter = SerialtvAdapter(context!!, datas)
        adapter.setOnItemClickListener(object : SerialtvAdapter.OnItemClickListener{
            override fun onItemClicked(data: Serialtv) {
                activity?.startActivity<DetailActivity>(
                    DetailActivity.DATA_INTENT_SERIAL to data,
                    DetailActivity.FLAG to "serial"
                )
            }
        })
        adapter.notifyDataSetChanged()
        rv_serialtv_search.layoutManager = LinearLayoutManager(activity)
        rv_serialtv_search.adapter = adapter
    }

    override fun onSucess() {
        stopShimmer()
    }

    override fun onError(message: String?) {
        stopShimmer()
    }

    private fun startShimmer() {
        rv_serialtv_search.visibility = View.INVISIBLE
        shimmer_layout.visibility = View.VISIBLE
        shimmer_layout.startShimmer()
    }

    private fun stopShimmer() {
        shimmer_layout.stopShimmer()
        shimmer_layout.visibility = View.GONE
        rv_serialtv_search.visibility = View.VISIBLE
    }

}
