package com.ericjohnson.footballapps.data.api

import com.google.gson.annotations.SerializedName

/**
 * Created by johnson on 04/10/18.
 */

data class TeamDetail(

        @SerializedName("idTeam")
        var idTeam: String,

        @SerializedName("strTeam")
        var strTeam: String? = null,

        @SerializedName("intFormedYear")
        var intFormedYear: String? = null,

        @SerializedName("strManager")
        var strManager: String? = null,

        @SerializedName("strStadium")
        var strStadium: String? = null,

        @SerializedName("strDescriptionEN")
        var strDescriptionEn: String? = null,

        @SerializedName("strWebsite")
        var strWebsite: String? = null,

        @SerializedName("strTeamBadge")
        var strTeamBadge: String? = null,

        @SerializedName("strSport")
        var strSport: String? = null
)