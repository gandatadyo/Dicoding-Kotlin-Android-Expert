package com.example.asus.aplikasifootballclub.model

import com.google.gson.annotations.SerializedName

data class gsonDetailMatchChildImage (
        @SerializedName("idTeam")
        var idTeam: String? = null,

        @SerializedName("strTeamBadge")
        var strTeamBadge: String? = null
)