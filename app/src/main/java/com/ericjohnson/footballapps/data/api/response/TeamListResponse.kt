package com.ericjohnson.footballapps.data.api.response

import com.ericjohnson.footballapps.data.api.TeamList
import com.google.gson.annotations.SerializedName

/**
 * Created by johnson on 10/22/18.
 */

data class TeamListResponse(
        @SerializedName("teams")
        var teams: MutableList<TeamList>
)