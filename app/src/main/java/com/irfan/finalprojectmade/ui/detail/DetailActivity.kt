package com.irfan.finalprojectmade.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.irfan.finalprojectmade.BuildConfig
import com.irfan.finalprojectmade.R
import com.irfan.finalprojectmade.model.Favorite
import com.irfan.finalprojectmade.model.Movie
import com.irfan.finalprojectmade.model.Serialtv
import com.irfan.finalprojectmade.ui.main.MainActivity
import com.irfan.finalprojectmade.ui.favorite.FavoriteViewModel
import com.irfan.finalprojectmade.util.displayImageOriginal
import com.irfan.finalprojectmade.util.parseDate
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.startActivity

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private var dataFlag: String = ""
    private var flagCheckTable: Boolean = false

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        startActivity<MainActivity>()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    companion object{
        const val DATA_INTENT_MOVIE = "intent_data_movie"
        const val DATA_INTENT_SERIAL = "intent_data_serial"
        const val DATA_INTENT_FAVORITE = "intent_data_favorite"
        const val FLAG = "intent_flag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel::class.java)
        fab_like_detail.setOnClickListener(this)

        dataFlag = intent.getStringExtra(FLAG)!!
        when (dataFlag) {
            "movie" -> {
                val data = intent.getParcelableExtra<Movie>(DATA_INTENT_MOVIE)
                checkData(data!!.id)
                tv_name_detail.text = data.title
                tv_release_detail.text = parseDate(data.releaseDate)
                tv_rating_detail.text = data.voteAverage.toString()
                tv_description_detail.text = data.overview
                tv_popularity_detail.text = data.popularity
                displayImageOriginal(this, iv_detail, BuildConfig.IMAGES + data.posterPath)
                if (data.voteAverage <= 6.0)
                    iv_rating_detail.setImageResource(R.drawable.ic_star_red_24dp)
                else
                    iv_rating_detail.setImageResource(R.drawable.ic_star_black_24dp)
                supportActionBar?.title = data.title
            }
            "serial" -> {
                val data = intent.getParcelableExtra<Serialtv>(DATA_INTENT_SERIAL)
                checkData(data?.id)
                tv_name_detail.text = data?.originalName
                tv_release_detail.text = parseDate(data?.firstAirDate!!)
                tv_rating_detail.text = data.voteAverage.toString()
                tv_description_detail.text = data.overview
                tv_popularity_detail.text = data.popularity
                displayImageOriginal(this, iv_detail, BuildConfig.IMAGES + data.posterPath)
                if (data.voteAverage <= 6.0)
                    iv_rating_detail.setImageResource(R.drawable.ic_star_red_24dp)
                else
                    iv_rating_detail.setImageResource(R.drawable.ic_star_black_24dp)
                supportActionBar?.title = data.originalName
            }
            "favorite" -> {
                val data = intent.getParcelableExtra<Favorite>(DATA_INTENT_FAVORITE)
                checkData(data!!.id.toString())
                tv_name_detail.text = data.title
                tv_release_detail.text = parseDate(data.releaseDate!!)
                tv_rating_detail.text = data.voteAverage.toString()
                tv_description_detail.text = data.overview
                tv_popularity_detail.text = data.popularity
                displayImageOriginal(this, iv_detail, BuildConfig.IMAGES + data.posterPath)
                if (data.voteAverage <= 6.0)
                    iv_rating_detail.setImageResource(R.drawable.ic_star_red_24dp)
                else
                    iv_rating_detail.setImageResource(R.drawable.ic_star_black_24dp)
                supportActionBar?.title = data.title
            }
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun checkData(id: String?) {
        Log.e("dataawal", id!!)
        favoriteViewModel.checkData(id.toLong())
        favoriteViewModel.getCheckData().observe(this, Observer { checkData ->
            flagCheckTable = if (checkData == 1){
                fab_like_detail.setImageResource(R.drawable.ic_favorite_black_24dp)
                true
            } else{
                fab_like_detail.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.fab_like_detail -> {
                /*
                Mengecek apakah film atau serial tv sudah tersipan di ROOM
                */
                if (flagCheckTable){
                    when (dataFlag) {
                        "movie" -> {
                            val data = intent.getParcelableExtra<Movie>(DATA_INTENT_MOVIE)
                            favoriteViewModel.deleteData(data?.id!!.toLong())
                        }
                        "serial" -> {
                            val data = intent.getParcelableExtra<Serialtv>(DATA_INTENT_SERIAL)
                            favoriteViewModel.deleteData(data?.id!!.toLong())
                        }
                        "favorite" -> {
                            val data = intent.getParcelableExtra<Favorite>(DATA_INTENT_FAVORITE)
                            favoriteViewModel.deleteData(data?.id!!)
                        }
                    }
                    fab_like_detail.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                }
                else{
                    if (dataFlag == "movie"){
                        val data = intent.getParcelableExtra<Movie>(DATA_INTENT_MOVIE)
                        favoriteViewModel.insert(
                            Favorite(
                            data!!.title,
                            data.voteAverage,
                            data.posterPath,
                            data.overview,
                            data.releaseDate,
                            data.popularity,
                            dataFlag,
                            data.id.toLong())
                        )

                    }

                    else if (dataFlag == "serial"){
                        val data = intent.getParcelableExtra<Serialtv>(DATA_INTENT_SERIAL)
                        favoriteViewModel.insert(Favorite(
                            data!!.originalName,
                            data.voteAverage,
                            data.posterPath,
                            data.overview,
                            data.firstAirDate,
                            data.popularity,
                            dataFlag,
                            data.id.toLong()))
                    }
                    flagCheckTable = true
                    fab_like_detail.setImageResource(R.drawable.ic_favorite_black_24dp)
                }
            }
        }
    }
}
