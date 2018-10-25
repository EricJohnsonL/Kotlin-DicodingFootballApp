package com.ericjohnson.footballapps.data.api.response

import com.ericjohnson.footballapps.data.api.League
import com.google.gson.annotations.SerializedName

/**
 * Created by johnson on 10/24/18.
 */
data class LeagueResponse(

        @SerializedName("leagues")
        var league: MutableList<League>

)