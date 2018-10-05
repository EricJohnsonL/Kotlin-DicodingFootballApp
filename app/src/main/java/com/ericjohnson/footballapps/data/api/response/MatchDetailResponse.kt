package com.ericjohnson.footballapps.data.api.response

import com.ericjohnson.footballapps.data.api.MatchDetail
import com.google.gson.annotations.SerializedName

/**
 * Created by johnson on 04/10/18.
 */

data class MatchDetailResponse(

        @SerializedName("events")
        var events: MutableList<MatchDetail>
)