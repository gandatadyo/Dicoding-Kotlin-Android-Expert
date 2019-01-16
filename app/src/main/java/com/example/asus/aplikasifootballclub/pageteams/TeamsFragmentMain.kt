package com.example.asus.aplikasifootballclub.pageteams

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.*
import com.example.asus.aplikasifootballclub.R
import com.example.asus.aplikasifootballclub.model.gsonLeague
import com.example.asus.aplikasifootballclub.model.gsonLeagueDetail
import com.example.asus.aplikasifootballclub.model.gsonTeams
import com.example.asus.aplikasifootballclub.model.gsonTeamsDetail
import com.example.asus.aplikasifootballclub.utils.ApiRepository
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_teams.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.ctx
import java.util.*


class TeamsFragmentMain : Fragment() {
    var recylerviewTeams: RecyclerView? = null
    var dataTeams: gsonTeams?=null
    var spinnerLeague: Spinner? = null
    var ArrayLeague = ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_teams, container, false)
        recylerviewTeams = rootView.recycler_Teams
        recylerviewTeams!!.layoutManager = GridLayoutManager(context, 1)
        spinnerLeague = rootView.spinner_Teams
        setHasOptionsMenu(true)
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
                            var response =  api.doRequest("https://www.thesportsdb.com/api/v1/json/1/lookup_all_teams.php?id="+datatempdetail[position].idLeague)
                            uiThread {
                                val gson = Gson()
                                dataTeams =  gson.fromJson(response, gsonTeams::class.java)
                                recylerviewTeams!!.adapter = TeamsAdapterMain(dataTeams!!.teams, context!!)
                            }
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu, menu)
        val searchViewItem = menu?.findItem(R.id.menuSearch)
        var searchView = searchViewItem?.actionView as SearchView
        searchView?.maxWidth= Int.MAX_VALUE
        searchView?.queryHint = "Search Teams"
        searchView?.setOnSearchClickListener {

        }

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                val api = ApiRepository()
                doAsync {
                    var response =  api.doRequest("https://www.thesportsdb.com/api/v1/json/1/searchteams.php?t="+query)
                    uiThread {
                        val gson = Gson()
                        dataTeams =  gson.fromJson(response, gsonTeams::class.java)
                        if(dataTeams?.teams!=null) {
                            recylerviewTeams!!.adapter = TeamsAdapterMain(dataTeams!!.teams, context!!)
                        }else{
                            val list = mutableListOf<gsonTeamsDetail>()
                            with(list){
                                clear()
                            }
                            recylerviewTeams!!.adapter = TeamsAdapterMain(list, context!!)
                        }
                    }
                }
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    class TeamsAdapterMain(private val teams: List<gsonTeamsDetail>, val context: Context): RecyclerView.Adapter<TeamViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
            return TeamViewHolder(TeamsUI().createView(AnkoContext.create(parent.context, parent)))
        }
        override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
            holder.bindItem(context,teams[position])
        }
        override fun getItemCount(): Int = teams.size
    }

    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val lblname: TextView = itemView.find(R.id.team_nameclub)
        private val lblimg: ImageView = itemView.find(R.id.team_img)
        fun bindItem(context: Context, teams: gsonTeamsDetail) {
            lblname.text = teams.strTeam
            Picasso.get().load(teams.strTeamBadge.toString()).into(lblimg)
            itemView.setOnClickListener{
                context.startActivity<TeamsDetail>("idTeam" to teams.idTeam.toString(),
                        "strDescriptionEN" to teams.strDescriptionEN.toString(),
                        "strTeam" to teams.strTeam.toString(),
                        "strTeamBadge" to teams.strTeamBadge.toString(),
                        "intFormedYear" to teams.intFormedYear.toString(),
                        "strStadium" to teams.strStadium.toString())
            }
        }
    }

    class TeamsUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>): View {
            return with(ui) {
                linearLayout {
                    lparams(width = matchParent, height = wrapContent) {
                        bottomMargin = dip(2)
                    }
                    padding = dip(5)
                    orientation = LinearLayout.HORIZONTAL
                    setBackgroundColor(resources.getColor(android.R.color.white))

                    imageView {
                        id = R.id.team_img
                        setImageResource(R.drawable.loading)
                        scaleType = ImageView.ScaleType.FIT_CENTER
                        textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    }.lparams(width = dip(100), height = dip(50)){
                        leftMargin = 15
                        topMargin = 5
                        bottomMargin = 5
                    }

                    relativeLayout{
                        textView {
                            id = R.id.team_nameclub
                            textSize = 16f
                        }.lparams(width = matchParent){
                            leftMargin = 15
                            topMargin = 25
                        }
                    }.lparams(height= matchParent)
                }
            }
        }
    }

}


