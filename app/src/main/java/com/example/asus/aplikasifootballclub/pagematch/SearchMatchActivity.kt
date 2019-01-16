package com.example.asus.aplikasifootballclub.pagematch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import com.example.asus.aplikasifootballclub.R
import com.example.asus.aplikasifootballclub.model.gsonMatchDetail
import com.example.asus.aplikasifootballclub.utils.ApiRepository
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search_match.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SearchMatchActivity : AppCompatActivity() {
    var searchView:SearchView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_match)
        recyerview_SearchView?.layoutManager = GridLayoutManager(applicationContext, 1)
    }

    data class gsonMatchSearch(val event: List<gsonMatchDetail>)

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchViewItem = menu?.findItem(R.id.menuSearch)
        searchView = searchViewItem?.actionView as SearchView
        searchView?.maxWidth= Int.MAX_VALUE
        searchView?.queryHint = "Search Match"
        searchView?.setIconified(false);
        searchView?.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                finish()
                return false
            }
        })

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                val api = ApiRepository()
                doAsync {
                    var response =  api.doRequest("https://www.thesportsdb.com/api/v1/json/1/searchevents.php?e="+query)
                    uiThread {
                        val gson = Gson()
                        var dataSearchMatch =  gson.fromJson(response, gsonMatchSearch::class.java)
                        if(dataSearchMatch?.event!=null) {
                            recyerview_SearchView.adapter = UtilsMatch.AdapterMain(dataSearchMatch?.event, applicationContext)
                        }else{
                            val list = mutableListOf<gsonMatchDetail>()
                            with(list){
                                clear()
                            }
                            recyerview_SearchView.adapter = UtilsMatch.AdapterMain(list, applicationContext)
                        }
                    }
                }
                return false
            }
        })
        return true
    }
}
