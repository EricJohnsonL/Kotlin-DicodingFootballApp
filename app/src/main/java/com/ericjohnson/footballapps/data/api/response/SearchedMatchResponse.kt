package com.ericjohnson.footballapps.data.api.response

import com.ericjohnson.footballapps.data.api.MatchDetail
import com.google.gson.annotations.SerializedName

/**
 * Created by johnson on 10/24/18.
 */
data class SearchedMatchResponse(
        @SerializedName("event")
        var event: MutableList<MatchDetail>
)