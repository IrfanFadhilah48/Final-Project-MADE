package com.irfan.finalprojectmade.ui.search

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.viewpager.widget.ViewPager
import com.irfan.finalprojectmade.R
import com.irfan.finalprojectmade.adapter.ViewPagerAdapter
import com.irfan.finalprojectmade.ui.search.movie.MovieSearchFragment
import com.irfan.finalprojectmade.ui.search.serialtv.SerialtvSearchFragment
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    private lateinit var adapter: ViewPagerAdapter
    private lateinit var searchView: SearchView
    private lateinit var parse: String
    private val serialTvSearchFragment = SerialtvSearchFragment()
    private val movieSearchFragment = MovieSearchFragment()

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        vp_search.isSaveFromParentEnabled = false
        tl_search.setupWithViewPager(vp_search)
        setViewPager(vp_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.search_text)
    }

    private fun setViewPager(vpSearch: ViewPager?) {
        adapter = ViewPagerAdapter(supportFragmentManager)
//        Log.e("datapasActivity", parse)
        adapter.addFragment(movieSearchFragment, "Movie")
        adapter.addFragment(serialTvSearchFragment, "Serial TV")
        vpSearch?.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val search = menu.findItem(R.id.search_data)
        searchView = search.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            @SuppressLint("DefaultLocale")
            override fun onQueryTextSubmit(query: String?): Boolean {
                parse = query?.toLowerCase().toString()
                serialTvSearchFragment.changingData(parse)
                movieSearchFragment.changingData(parse)

                val methodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                methodManager.hideSoftInputFromWindow(searchView.windowToken, 0)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
