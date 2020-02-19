package com.irfan.favoriteapp.adapter

import android.content.Context
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.irfan.favoriteapp.model.Favorite
import com.irfan.favoriteapp.BuildConfig
import com.irfan.favoriteapp.R
import com.irfan.favoriteapp.util.displayImageOriginal
import com.irfan.favoriteapp.util.parseDate
import kotlinx.android.synthetic.main.item_rv.view.*

class FavoriteAdapter(val context: Context): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(){

    private lateinit var onItemClickListener: OnItemClickListener
    private var listData = arrayListOf<Favorite>()
    private var mCursor: Cursor? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataPosition = listData[position]

        holder.tvNama.text = dataPosition.title
        holder.tvRating.text = dataPosition.voteAverage.toString()
        holder.tvRelease.text = parseDate(dataPosition.releaseDate.toString())
        holder.tvPopularity.text = dataPosition.popularity
        displayImageOriginal(context, holder.ivPhoto, BuildConfig.IMAGES + dataPosition.posterPath)
        if (dataPosition.voteAverage <= 6.0)
            holder.ivStar.setImageResource(R.drawable.ic_star_red_24dp)
        else
            holder.ivStar.setImageResource(R.drawable.ic_star_black_24dp)

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClicked(dataPosition)
        }

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClicked(dataPosition)
        }
    }

    fun setDataFavorite(datas: ArrayList<Favorite>){
        listData = datas
        Log.e("datanyaaa", listData.toString())
        notifyDataSetChanged()
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
        fun onItemClicked(favorite: Favorite)
    }
}