package com.ericjohnson.footballapps.data.api.response

import com.ericjohnson.footballapps.data.api.PlayerDetail
import com.google.gson.annotations.SerializedName

/**
 * Created by johnson on 10/22/18.
 */
data class PlayerResponse(

        @SerializedName("player")
        var player: MutableList<PlayerDetail>
)