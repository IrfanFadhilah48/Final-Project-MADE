package com.irfan.finalprojectmade.ui.serialtv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.irfan.finalprojectmade.R
import com.irfan.finalprojectmade.adapter.SerialtvAdapter
import com.irfan.finalprojectmade.model.Serialtv
import com.irfan.finalprojectmade.network.ApiListener
import com.irfan.finalprojectmade.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_serialtv.*
import kotlinx.android.synthetic.main.shimmer_layout.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SerialtvFragment : Fragment(), ApiListener {

    private lateinit var serialtvViewModel: SerialtvViewModel
    private lateinit var adapter: SerialtvAdapter
    private var datas = arrayListOf<Serialtv>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        serialtvViewModel = ViewModelProviders.of(this).get(SerialtvViewModel::class.java)
        return inflater.inflate(R.layout.fragment_serialtv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        if (isAdded){
        if (activity != null && isAdded){
            initObserver()
            fetchData()
        }

//        }
    }

    private fun initObserver() {
        serialtvViewModel.apiListener = this
        serialtvViewModel.fetchData().observe(this, Observer { listData ->
            showData(listData)
        })
    }

    private fun fetchData() {
        startShimmer()
        serialtvViewModel.getData()
    }

    private fun showData(listData: List<Serialtv>) {
        datas.addAll(listData)
        rv_serialtv.layoutManager = LinearLayoutManager(activity)
        adapter = SerialtvAdapter(context!!, datas)
        adapter.setOnItemClickListener(object : SerialtvAdapter.OnItemClickListener {
            override fun onItemClicked(data: Serialtv) {
                activity?.toast(data.originalName!!)
                activity?.startActivity<DetailActivity>(
                    DetailActivity.DATA_INTENT_SERIAL to data,
                    DetailActivity.FLAG to "serial"
                )
                activity?.finish()
            }
        })
        adapter.notifyDataSetChanged()
        rv_serialtv.adapter = adapter
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
        rv_serialtv.visibility = View.INVISIBLE
        shimmer_layout.visibility = View.VISIBLE
        shimmer_layout.startShimmer()
    }

    private fun stopShimmer() {
        shimmer_layout.stopShimmer()
        shimmer_layout.visibility = View.GONE
        rv_serialtv.visibility = View.VISIBLE
    }

}