package com.example.asus.aplikasifootballclub.pagematch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

import com.example.asus.aplikasifootballclub.R
import com.example.asus.aplikasifootballclub.model.gsonLeague
import com.example.asus.aplikasifootballclub.model.gsonLeagueDetail
import com.example.asus.aplikasifootballclub.model.gsonMatch
import com.example.asus.aplikasifootballclub.utils.ApiRepository
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_match_next.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.uiThread

class NextFragment : Fragment() {
    var recylerviewMatch: RecyclerView? = null
    var dataNextMatch: gsonMatch?=null
    var spinnerLeague: Spinner? = null
    var ArrayLeague = ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_match_next, container, false)
        recylerviewMatch = rootView.recycler_Match_Next
        recylerviewMatch?.layoutManager = GridLayoutManager(context, 1)
        spinnerLeague = rootView.spinner_Match_Next
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val api = ApiRepository()
        doAsync {
            var response =  api.doRequest("https://www.thesportsdb.com/api/v1/json/1/all_leagues.php")
            uiThread {
                val gson = Gson()
                val datatemp =  gson.fromJson(response, gsonLeague::class.java)
                val datatempdetail : List<gsonLeagueDetail> = datatemp.leagues
                ArrayLeague.clear()
                for(a in 1..datatempdetail.size){
                    if(datatempdetail[a-1].strSport.toString()=="Soccer") {
                        ArrayLeague.add(datatempdetail[a - 1].strLeague.toString())
                    }
                }
                var spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, ArrayLeague)
                spinnerLeague?.adapter = spinnerAdapter
                spinnerLeague?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                        doAsync {
                            var response =  api.doRequest("https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id="+datatempdetail[position].idLeague)
                            uiThread {
                                changeDataRecylerview(response)
                            }
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }
            }
        }
    }

    fun changeDataRecylerview(response:String){
        val gson = Gson()
        dataNextMatch =  gson.fromJson(response, gsonMatch::class.java)
        recylerviewMatch!!.adapter = UtilsMatch.AdapterMain(dataNextMatch!!.events, context!!)
    }
}
