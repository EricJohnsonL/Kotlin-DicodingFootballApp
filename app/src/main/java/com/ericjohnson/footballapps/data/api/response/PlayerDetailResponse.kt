package com.ericjohnson.footballapps.data.api.response

import com.ericjohnson.footballapps.data.api.PlayerDetail
import com.google.gson.annotations.SerializedName

/**
 * Created by johnson on 10/23/18.
 */

data class PlayerDetailResponse(

        @SerializedName("players")
        var player: MutableList<PlayerDetail>
)