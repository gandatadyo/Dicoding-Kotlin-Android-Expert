package com.example.asus.aplikasifootballclub.model

import com.google.gson.annotations.SerializedName

data class gsonLeagueDetail(
        @SerializedName("idLeague")
        var idLeague: String? = null,

        @SerializedName("strLeague")
        var strLeague: String? = null,

        @SerializedName("strSport")
        var strSport: String? = null
)