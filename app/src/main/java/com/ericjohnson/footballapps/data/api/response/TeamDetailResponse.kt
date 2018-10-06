package com.ericjohnson.footballapps.data.api.response

import com.ericjohnson.footballapps.data.api.TeamDetail
import com.google.gson.annotations.SerializedName

/**
 * Created by johnson on 04/10/18.
 */

data class TeamDetailResponse(

        @SerializedName("teams")
        var teams: MutableList<TeamDetail>
)