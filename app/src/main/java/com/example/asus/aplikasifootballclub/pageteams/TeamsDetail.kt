package com.example.asus.aplikasifootballclub.pageteams

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.asus.aplikasifootballclub.utils.FavoriteTeamsDatabase
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.asus.aplikasifootballclub.R
import com.example.asus.aplikasifootballclub.model.gsonPlayersDetail
import com.example.asus.aplikasifootballclub.utils.ApiRepository
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_teams_detail.*
import kotlinx.android.synthetic.main.fragment_teams_detail_overview.view.*
import kotlinx.android.synthetic.main.fragment_teams_detail_players.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import com.example.asus.aplikasifootballclub.utils.database
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

class TeamsDetail : AppCompatActivity() {
    private var menuItem: Menu? = null
    var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams_detail)
        lblstadion_team.text = intent.getStringExtra("strStadium")
        lblname_team.text = intent.getStringExtra("strTeam")
        lbl_year_team.text = intent.getStringExtra("intFormedYear")
        Picasso.get().load(intent.getStringExtra("strTeamBadge")).into(img_team_logo)
//        setSupportActionBar(tollbar_teamsdetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val adapter = SampleAdapter(supportFragmentManager, intent)
        viewpagger_teamsdetail.adapter = adapter
        tabs_teamsDetail.setupWithViewPager(viewpagger_teamsdetail)
        favoriteState()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menuitemfavorite, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()
                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun addToFavorite(){
        try {
            database.use {
                insert(FavoriteTeamsDatabase.TABLE_FAVORITE_TEAMS,
                        FavoriteTeamsDatabase.idTeam to intent.getStringExtra("idTeam").toString(),
                        FavoriteTeamsDatabase.strDescriptionEN to intent.getStringExtra("strDescriptionEN").toString(),
                        FavoriteTeamsDatabase.strTeam to intent.getStringExtra("strTeam").toString(),
                        FavoriteTeamsDatabase.strTeamBadge to intent.getStringExtra("strTeamBadge").toString(),
                        FavoriteTeamsDatabase.intFormedYear to intent.getStringExtra("intFormedYear").toString(),
                        FavoriteTeamsDatabase.strStadium to intent.getStringExtra("strStadium").toString())
            }
            snackbar(contentView!!, "Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            toast("Error")
        }
    }

    fun removeFromFavorite(){
        try {
            database.use {
                delete(FavoriteTeamsDatabase.TABLE_FAVORITE_TEAMS, "(idTeam = {id})","id" to intent.getStringExtra("idTeam").toString())
            }
            snackbar(contentView!!, "Delete from favorite").show()
        } catch (e: SQLiteConstraintException){
            toast("Error")
        }
    }

    fun favoriteState(){
        database.use {
            val result = select(FavoriteTeamsDatabase.TABLE_FAVORITE_TEAMS).whereArgs("(idTeam = {id})","id" to intent.getStringExtra("idTeam"))
            val favorite = result.parseList(classParser<FavoriteTeamsDatabase>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    class SampleAdapter(fm: FragmentManager, intdata: Intent) : FragmentPagerAdapter(fm) {
        var intent:Intent = intdata

        override fun getItem(position: Int): Fragment? = when (position) {
            0 -> {
                TeamsDetailOverview.newInstance(intent.getStringExtra("strDescriptionEN"))
                 }
            1 -> {
                TeamsDetailPlayers.newInstance(intent.getStringExtra("strTeam"))
                 }
            else -> null
        }
        override fun getPageTitle(position: Int): CharSequence = when (position) {
            0 -> "Overview"
            else -> "Players"
        }
        override fun getCount(): Int = 2
    }

    class TeamsDetailOverview : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_teams_detail_overview, container, false)
            var args = getArguments()
            rootView.lblOverView_TeamsDetail.text=args?.getString("strDescriptionEN")
            return rootView
        }

        companion object {
            fun newInstance(strDescriptionEN: String): TeamsDetailOverview {
                val fragment = TeamsDetailOverview()
                val args = Bundle()
                args.putString("strDescriptionEN", strDescriptionEN)
                fragment.setArguments(args)
                return fragment
            }
        }
    }

    class TeamsDetailPlayers : Fragment() {
        var recylerviewPlayers: RecyclerView? = null
        var dataListPlayers: gsonPlayers?=null

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_teams_detail_players, container, false)
            recylerviewPlayers = rootView.recyclerview_detail_players
            recylerviewPlayers!!.layoutManager = GridLayoutManager(context, 1)
            return rootView
        }

        companion object {
            fun newInstance(strTeam: String): TeamsDetailPlayers {
                val fragment = TeamsDetailPlayers()
                val args = Bundle()
                args.putString("strTeam", strTeam)
                fragment.setArguments(args)
                return fragment
            }
        }

        data class gsonPlayers(val player: List<gsonPlayersDetail>)

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            val api = ApiRepository()
            doAsync {
                print("https://www.thesportsdb.com/api/v1/json/1/searchplayers.php?t="+arguments?.getString("strTeam"))
                var response =  api.doRequest("https://www.thesportsdb.com/api/v1/json/1/searchplayers.php?t="+arguments?.getString("strTeam"))
                uiThread {
                    val gson = Gson()
                    dataListPlayers =  gson.fromJson(response, gsonPlayers::class.java)
                    recylerviewPlayers?.adapter = PlayersAdapterMain(dataListPlayers?.player!!, context!!)
                }
            }
        }

        class PlayersAdapterMain(private val players: List<gsonPlayersDetail>, val context: Context): RecyclerView.Adapter<TeamViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
                return TeamViewHolder(PlayersListUI().createView(AnkoContext.create(parent.context, parent)))
            }
            override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
                holder.bindItem(context,players[position])
            }
            override fun getItemCount(): Int = players.size
        }

        class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            private val lblname: TextView = itemView.find(R.id.playersname)
            private val lblimg: ImageView = itemView.find(R.id.playerimg)
            fun bindItem(context: Context, dataListPlayerstemp: gsonPlayersDetail) {
                lblname.text = dataListPlayerstemp.strPlayer
                Picasso.get().load(dataListPlayerstemp.strCutout.toString()).into(lblimg)
                itemView.setOnClickListener{
                    context.startActivity<TeamsDetailPlayer>("idPlayers" to dataListPlayerstemp.idPlayer.toString(),
                            "strPlayer" to dataListPlayerstemp.strPlayer.toString(),
                            "strFanart1" to dataListPlayerstemp.strFanart1.toString(),
                            "strDescriptionEN" to dataListPlayerstemp.strDescriptionEN.toString(),
                            "strPosition" to dataListPlayerstemp.strPosition.toString(),
                            "strHeight" to dataListPlayerstemp.strHeight.toString(),
                            "strWeight" to dataListPlayerstemp.strWeight.toString())
                }
            }
        }

        class PlayersListUI : AnkoComponent<ViewGroup> {
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
                            id = R.id.playerimg
                            setImageResource(R.drawable.loading)
                            scaleType = ImageView.ScaleType.FIT_CENTER
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                        }.lparams(width = dip(100), height = dip(50)){
                            leftMargin = 15
                            topMargin = 5
                            bottomMargin = 5
                        }

                        textView {
                            id = R.id.playersname
                            textSize = 16f
                        }.lparams(width = matchParent){
                            leftMargin = 15
                            topMargin = 20
                        }
                    }
                }
            }
        }
    }
}
