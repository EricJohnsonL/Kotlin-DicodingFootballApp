package com.ericjohnson.footballapps.data.api

import com.google.gson.annotations.SerializedName

/**
 * Created by johnson on 04/10/18.
 */

data class TeamDetail(

        @SerializedName("strTeamBadge")
        private var strTeamBadge: String? = null
)