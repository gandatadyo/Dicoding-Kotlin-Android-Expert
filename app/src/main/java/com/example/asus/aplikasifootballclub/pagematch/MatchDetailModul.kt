package com.example.asus.aplikasifootballclub.pagematch

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.asus.aplikasifootballclub.R
import com.example.asus.aplikasifootballclub.utils.FavoriteMatchDatabase
import com.example.asus.aplikasifootballclub.utils.database
import com.example.asus.aplikasifootballclub.model.gsonDetailMatchChildImage
import com.example.asus.aplikasifootballclub.model.gsonDetailMatchImage
import com.example.asus.aplikasifootballclub.model.gsonMatch
import com.example.asus.aplikasifootballclub.utils.ApiRepository
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.nestedScrollView

class MatchDetailModul(val view: MatchDetailActivity, val context: Context, val intent: Intent){
    val api = ApiRepository()

    fun getdataDetailDetailTeam(){
        doAsync {
            var response =  api.doRequest("https://www.thesportsdb.com/api/v1/json/1/lookupevent.php?id="+intent.getStringExtra("idEvent"))
            uiThread {
                val gson = Gson()
                val dataDetailDetailTeam = gson.fromJson(response, gsonMatch::class.java)
                view.dataDetail = dataDetailDetailTeam.events[0]
                view.setdataDetailViewUI()
            }
        }
    }

    fun getDetailTeam(IDTeam:String,img: ImageView){
        doAsync {
            var response =  api.doRequest("https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id="+IDTeam)
            uiThread {
                val gson = Gson()
                val dataDetail1 = gson.fromJson(response, gsonDetailMatchImage::class.java)
                val teams1: gsonDetailMatchChildImage = dataDetail1.teams[0]
                Picasso.get().load(teams1.strTeamBadge.toString()).into(img)
            }
        }
    }

    fun addToFavorite(){
        try {
            view.database.use {
                insert(FavoriteMatchDatabase.TABLE_FAVORITE_MATCH,
                        FavoriteMatchDatabase.TEAM_ID to intent.getStringExtra("idEvent"),
                        FavoriteMatchDatabase.TEAM_NAME_HOME to view.dataDetail?.strHomeTeam,
                        FavoriteMatchDatabase.TEAM_NAME_AWAY to view.dataDetail?.strAwayTeam,
                        FavoriteMatchDatabase.TEAM_SCORE_HOME to view.dataDetail?.intHomeScore,
                        FavoriteMatchDatabase.TEAM_SCORE_AWAY to view.dataDetail?.intAwayScore,
                        FavoriteMatchDatabase.DATE_EVENT to view.dataDetail?.dateEvent,
                        FavoriteMatchDatabase.DATE_TIME to view.dataDetail?.strTime)
            }
            snackbar(view.contentView!!, "Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            view.toast("Error")
        }
    }

    fun removeFromFavorite(){
        try {
            view.database.use {
                delete(FavoriteMatchDatabase.TABLE_FAVORITE_MATCH, "(TEAM_ID = {id})","id" to intent.getStringExtra("idEvent"))
            }
            snackbar(view.contentView!!, "Delete from favorite").show()
        } catch (e: SQLiteConstraintException){
            view.toast("Error")
        }
    }

    fun favoriteState(){
        view.database.use {
            val result = select(FavoriteMatchDatabase.TABLE_FAVORITE_MATCH).whereArgs("(TEAM_ID = {id})","id" to intent.getStringExtra("idEvent"))
            val favorite = result.parseList(classParser<FavoriteMatchDatabase>())
            if (!favorite.isEmpty()) view.isFavorite = true
        }
    }

}