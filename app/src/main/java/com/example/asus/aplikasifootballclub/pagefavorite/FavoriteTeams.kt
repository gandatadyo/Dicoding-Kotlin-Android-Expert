package com.example.asus.aplikasifootballclub.pagefavorite


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.example.asus.aplikasifootballclub.R
import com.example.asus.aplikasifootballclub.utils.FavoriteTeamsDatabase
import com.example.asus.aplikasifootballclub.utils.database
import com.example.asus.aplikasifootballclub.pageteams.TeamsDetail
import com.example.asus.aplikasifootballclub.pageteams.TeamsFragmentMain
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_favorite_teams.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteTeams : Fragment() {
    var recylerviewFavoriteTeams: RecyclerView? = null
    private var favorites: MutableList<FavoriteTeamsDatabase> = mutableListOf()
    private lateinit var adapter: AdapterMain

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_favorite_teams, container, false)
        recylerviewFavoriteTeams = rootView.recycler_favorite_teams
        recylerviewFavoriteTeams?.layoutManager = GridLayoutManager(context, 1)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    class AdapterMain(private val teams: List<FavoriteTeamsDatabase>, val context: Context): RecyclerView.Adapter<AdapterMainHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterMainHolder {
            return AdapterMainHolder(TeamsFragmentMain.TeamsUI().createView(AnkoContext.create(parent.context, parent)))
        }
        override fun onBindViewHolder(holder: AdapterMainHolder, position: Int) {
            holder.bindItem(context,teams[position])
        }
        override fun getItemCount(): Int = teams.size
    }

    class AdapterMainHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val lblname: TextView = itemView.find(R.id.team_nameclub)
        private val lblimg: ImageView = itemView.find(R.id.team_img)
        fun bindItem(context: Context, teams: FavoriteTeamsDatabase) {
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

    private fun showFavorite(){
        context?.database?.use {
            favorites.clear()
            val result = select(FavoriteTeamsDatabase.TABLE_FAVORITE_TEAMS)
            val favorite1 = result.parseList(classParser<FavoriteTeamsDatabase>())
            favorites.addAll(favorite1)
            adapter = AdapterMain(favorites, context!!)
            recylerviewFavoriteTeams?.adapter = adapter
        }
    }
}
