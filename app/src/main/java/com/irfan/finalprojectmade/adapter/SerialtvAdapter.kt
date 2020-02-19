package com.irfan.finalprojectmade.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.irfan.finalprojectmade.R
import com.irfan.finalprojectmade.model.Serialtv
import com.irfan.finalprojectmade.BuildConfig
import com.irfan.finalprojectmade.util.displayImageOriginal
import com.irfan.finalprojectmade.util.parseDate
import kotlinx.android.synthetic.main.item_rv.view.*

class SerialtvAdapter (private val context: Context, private val datas: ArrayList<Serialtv>): RecyclerView.Adapter<SerialtvAdapter.ViewHolder>(){

    private lateinit var onItemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = datas[position]

        holder.tvNama.text = data.originalName
        holder.tvRating.text = data.voteAverage.toString()
        holder.tvPopularity.text = data.popularity
        holder.tvRelease.text = parseDate(data.firstAirDate!!)
        displayImageOriginal(context, holder.ivPhoto, BuildConfig.IMAGES + data.posterPath)
        if (data.voteAverage <= 6.0)
            holder.ivStar.setImageResource(R.drawable.ic_star_red_24dp)
        else
            holder.ivStar.setImageResource(R.drawable.ic_star_black_24dp)
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClicked(data)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNama: TextView = itemView.tv_name
        var tvRating: TextView = itemView.tv_rating
        var ivPhoto: ImageView = itemView.iv_photo
        var ivStar: ImageView = itemView.iv_star_rating
        var tvRelease: TextView = itemView.tv_release
        var tvPopularity: TextView = itemView.tv_popularity
    }

    fun setOnItemClickListener(onItemClickListeners: OnItemClickListener){
        this.onItemClickListener = onItemClickListeners
    }

    interface OnItemClickListener{
        fun onItemClicked(data: Serialtv)
    }
}