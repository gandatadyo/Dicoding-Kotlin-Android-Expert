package com.example.asus.aplikasifootballclub.pagefavorite


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView

import com.example.asus.aplikasifootballclub.R
import com.example.asus.aplikasifootballclub.utils.FavoriteMatchDatabase
import com.example.asus.aplikasifootballclub.utils.database
import com.example.asus.aplikasifootballclub.pagematch.MatchDetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import java.text.SimpleDateFormat
import java.util.*

class FavoriteMatch : Fragment() , AnkoComponent<Context> {

    private var favorites: MutableList<FavoriteMatchDatabase> = mutableListOf()
    private lateinit var adapter: FavoriteTeamsAdapter
    private lateinit var listEvent: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = FavoriteTeamsAdapter(favorites) {
            ctx.startActivity<MatchDetailActivity>("idEvent" to "${it.teamid}")
        }
        listEvent.adapter = adapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            swipeRefresh = swipeRefreshLayout {
                listEvent = recyclerView {
                    id = R.id.recylermatch_favorite
                    lparams (width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }
            swipeRefresh.onRefresh {
                showFavorite()
            }
        }
    }

    class FavoriteTeamsAdapter(private val favorite: List<FavoriteMatchDatabase>, private val listener: (FavoriteMatchDatabase) -> Unit): RecyclerView.Adapter<FavoriteTeamsAdapterHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTeamsAdapterHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.list_match, parent, false)
            return FavoriteTeamsAdapterHolder(view)
        }
        override fun onBindViewHolder(holder: FavoriteTeamsAdapterHolder, position: Int) {
            holder.bindItem(favorite[position], listener)
        }
        override fun getItemCount(): Int = favorite.size
    }

    class FavoriteTeamsAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val lbldate: TextView = itemView.find(R.id.lblDate_list)
        private val lbltime: TextView = itemView.find(R.id.lblTime_List)
        private val lblhome: TextView = itemView.find(R.id.lblhomeclub_list)
        private val lblaway: TextView = itemView.find(R.id.lblaway_list)
        private val btnAlrm: ImageButton = itemView.find(R.id.imageButton) as ImageButton
        fun toGMTFormat(date: String, time: String): Date? {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            val dateTime = "$date $time"
            return formatter.parse(dateTime)
        }
        fun Date.getStringTimeStampWithTime(): String {
            val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            return dateFormat.format(this)
        }
        fun Date.getStringTimeStampWithDate(): String {
            val dateFormat = SimpleDateFormat(" dd MM yyyy", Locale.getDefault())
            return dateFormat.format(this)
        }
        fun bindItem(teams: FavoriteMatchDatabase, listener: (FavoriteMatchDatabase) -> Unit) {
            var datet = toGMTFormat(teams.teamdate.toString(),teams.teamtime!!.replace("+00:00",""))
            lbldate.text = datet?.getStringTimeStampWithDate()
            lbltime.text = datet?.getStringTimeStampWithTime()
            btnAlrm.visibility = View.GONE
            lblhome.text = teams.teamnamehome
            lblaway.text = teams.teamnameaway
            itemView.onClick {
                listener(teams)
            }
        }
    }

    private fun showFavorite(){
        context?.database?.use {
            favorites.clear()
            val result = select(FavoriteMatchDatabase.TABLE_FAVORITE_MATCH)
            val favorite1 = result.parseList(classParser<FavoriteMatchDatabase>())
            favorites.addAll(favorite1)
            adapter.notifyDataSetChanged()
            swipeRefresh.isRefreshing = false
        }
    }
}
