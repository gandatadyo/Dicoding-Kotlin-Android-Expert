package com.example.asus.aplikasifootballclub.pagematch

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.asus.aplikasifootballclub.R
import com.example.asus.aplikasifootballclub.R.drawable.ic_add_to_favorites
import com.example.asus.aplikasifootballclub.R.drawable.ic_added_to_favorites
import com.example.asus.aplikasifootballclub.R.menu.menuitemfavorite
import com.example.asus.aplikasifootballclub.model.gsonMatchDetail
import org.jetbrains.anko.*

class MatchDetailActivity : AppCompatActivity() {
    private var menuItem: Menu? = null
    var isFavorite: Boolean = false
    var dataDetail:gsonMatchDetail? = null
    private var view: View?= null
    var detailModul: MatchDetailModul?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UtilsMatch.ShceduleLeagueDetailUI().setContentView(this)
        view = contentView
        detailModul = MatchDetailModul(this, this, intent)
        detailModul?.getdataDetailDetailTeam()
        detailModul?.favoriteState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun setdataDetailViewUI(){
        val dateEvent = findViewById<TextView>(UtilsMatch.ShceduleLeagueDetailUI.dateEvent)
        val strHomeTeam = findViewById<TextView>(UtilsMatch.ShceduleLeagueDetailUI.strHomeTeam)
        val strAwayTeam = findViewById<TextView>(UtilsMatch.ShceduleLeagueDetailUI.strAwayTeam)
        val strHomeGoalDetails = findViewById<TextView>(UtilsMatch.ShceduleLeagueDetailUI.strHomeGoalDetails)
        val strAwayGoalDetails = findViewById<TextView>(UtilsMatch.ShceduleLeagueDetailUI.strAwayGoalDetails)
        val intHomeScore = findViewById<TextView>(UtilsMatch.ShceduleLeagueDetailUI.intHomeScore)
        val intAwayScore = findViewById<TextView>(UtilsMatch.ShceduleLeagueDetailUI.intAwayScore)
        val strHomeLineupGoalkeeper = findViewById<TextView>(UtilsMatch.ShceduleLeagueDetailUI.strHomeLineupGoalkeeper)
        val strAwayLineupGoalkeeper = findViewById<TextView>(UtilsMatch.ShceduleLeagueDetailUI.strAwayLineupGoalkeeper)
        val strHomeLineupDefense = findViewById<TextView>(UtilsMatch.ShceduleLeagueDetailUI.strHomeLineupDefense)
        val strAwayLineupDefense = findViewById<TextView>(UtilsMatch.ShceduleLeagueDetailUI.strAwayLineupDefense)
        val strHomeLineupMidfield = findViewById<TextView>(UtilsMatch.ShceduleLeagueDetailUI.strHomeLineupMidfield)
        val strAwayLineupMidfield = findViewById<TextView>(UtilsMatch.ShceduleLeagueDetailUI.strAwayLineupMidfield)
        val strHomeLineupForward = findViewById<TextView>(UtilsMatch.ShceduleLeagueDetailUI.strHomeLineupForward)
        val strAwayLineupForward = findViewById<TextView>(UtilsMatch.ShceduleLeagueDetailUI.strAwayLineupForward)
        val strHomeLineupSubstitutes = findViewById<TextView>(UtilsMatch.ShceduleLeagueDetailUI.strHomeLineupSubstitutes)
        val strAwayLineupSubstitutes = findViewById<TextView>(UtilsMatch.ShceduleLeagueDetailUI.strAwayLineupSubstitutes)
        dateEvent.text = dataDetail?.dateEvent
        strHomeTeam.text = dataDetail?.strHomeTeam
        strAwayTeam.text = dataDetail?.strAwayTeam
        strHomeGoalDetails.text = dataDetail?.strHomeGoalDetails
        strAwayGoalDetails.text = dataDetail?.strAwayGoalDetails
        intHomeScore.text = dataDetail?.intHomeScore
        intAwayScore.text = dataDetail?.intAwayScore
        strHomeLineupGoalkeeper.text = dataDetail?.strHomeLineupGoalkeeper
        strAwayLineupGoalkeeper.text = dataDetail?.strAwayLineupGoalkeeper
        strHomeLineupDefense.text = dataDetail?.strHomeLineupDefense
        strAwayLineupDefense.text = dataDetail?.strAwayLineupDefense
        strHomeLineupMidfield.text = dataDetail?.strHomeLineupMidfield
        strAwayLineupMidfield.text = dataDetail?.strAwayLineupMidfield
        strHomeLineupForward.text = dataDetail?.strHomeLineupForward
        strAwayLineupForward.text = dataDetail?.strAwayLineupForward
        strHomeLineupSubstitutes.text = dataDetail?.strHomeLineupSubstitutes
        strAwayLineupSubstitutes.text = dataDetail?.strAwayLineupSubstitutes

        val imagestrHomeTeam = findViewById<ImageView>(UtilsMatch.ShceduleLeagueDetailUI.imagestrHomeTeam)
        val imagestrAwayTeam = findViewById<ImageView>(UtilsMatch.ShceduleLeagueDetailUI.imagestrAwayTeam)
        val idHomeTeam =dataDetail?.idHomeTeam.toString()
        val idAwayTeam =dataDetail?.idAwayTeam.toString()
        detailModul?.getDetailTeam(idHomeTeam,imagestrHomeTeam)
        detailModul?.getDetailTeam(idAwayTeam,imagestrAwayTeam)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(menuitemfavorite, menu)
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
                if (isFavorite) detailModul?.removeFromFavorite() else detailModul?.addToFavorite()
                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }
}