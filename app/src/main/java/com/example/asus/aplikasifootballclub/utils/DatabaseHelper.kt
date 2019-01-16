package com.example.asus.aplikasifootballclub.utils


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class databaseHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "Favorite.db", null, 1) {
    companion object {
        private var instance: databaseHelper? = null
        @Synchronized
        fun getInstance(ctx: Context): databaseHelper {
            if (instance == null) {
                instance = databaseHelper(ctx.applicationContext)
            }
            return instance as databaseHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(FavoriteMatchDatabase.TABLE_FAVORITE_MATCH, true,
        FavoriteMatchDatabase.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteMatchDatabase.TEAM_ID to TEXT ,
                FavoriteMatchDatabase.TEAM_NAME_HOME to TEXT,
                FavoriteMatchDatabase.TEAM_NAME_AWAY to TEXT,
                FavoriteMatchDatabase.TEAM_SCORE_HOME to TEXT,
                FavoriteMatchDatabase.TEAM_SCORE_AWAY to TEXT,
                FavoriteMatchDatabase.DATE_EVENT to TEXT,
                FavoriteMatchDatabase.DATE_TIME  to TEXT)
        db.createTable(FavoriteTeamsDatabase.TABLE_FAVORITE_TEAMS, true,
                FavoriteTeamsDatabase.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteTeamsDatabase.idTeam to TEXT ,
                FavoriteTeamsDatabase.strDescriptionEN to TEXT,
                FavoriteTeamsDatabase.strTeam to TEXT,
                FavoriteTeamsDatabase.strTeamBadge to TEXT,
                FavoriteTeamsDatabase.intFormedYear to TEXT,
                FavoriteTeamsDatabase.strStadium to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoriteMatchDatabase.TABLE_FAVORITE_MATCH, true)
        db.dropTable(FavoriteTeamsDatabase.TABLE_FAVORITE_TEAMS, true)
    }
}

val Context.database: databaseHelper get() = databaseHelper.getInstance(applicationContext)

data class FavoriteMatchDatabase(val id: Int?, val teamid: String?, val teamnamehome: String?, val teamnameaway: String?, val teamscorehome: String?, val teamscoreaway: String?,val teamdate: String?,val teamtime: String?) {
    companion object {
        const val TABLE_FAVORITE_MATCH: String = "TABLE_FAVORITE"
        const val ID: String = "ID"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME_HOME: String = "TEAM_NAME_HOME"
        const val TEAM_NAME_AWAY: String = "TEAM_NAME_AWAY"
        const val TEAM_SCORE_HOME: String = "TEAM_SCORE_HOME"
        const val TEAM_SCORE_AWAY: String = "TEAM_SCORE_AWAY"
        const val DATE_EVENT: String = "DATE_EVENT"
        const val DATE_TIME: String = "DATE_TIME"
    }
}

data class FavoriteTeamsDatabase(val id: Int?, val idTeam: String?, val strDescriptionEN: String?, val strTeam: String?, val strTeamBadge: String?, val intFormedYear: String?,val strStadium: String?) {
    companion object {
        const val TABLE_FAVORITE_TEAMS: String = "TABLE_FAVORITE_TEAMS"
        const val ID: String = "ID"
        const val idTeam: String = "idTeam"
        const val strDescriptionEN: String = "strDescriptionEN"
        const val strTeam: String = "strTeam"
        const val strTeamBadge: String = "strTeamBadge"
        const val intFormedYear: String = "intFormedYear"
        const val strStadium: String = "strStadium"
    }
}