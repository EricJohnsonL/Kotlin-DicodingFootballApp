package com.ericjohnson.footballapps.data.api

import com.google.gson.annotations.SerializedName

/**
 * Created by johnson on 10/22/18.
 */

data class PlayerDetail(

        @SerializedName("idPlayer")
        var idPlayer: String,

        @SerializedName("strPlayer")
        var strPlayer: String? = null,

        @SerializedName("strNationality")
        var strNationality: String? = null,

        @SerializedName("strPosition")
        var strPosition: String? = null,

        @SerializedName("strDescriptionEN")
        var strDescriptionEN: String? = null,

        @SerializedName("strHeight")
        var strHeight: String? = null,

        @SerializedName("strWeight")
        var strWeight: String? = null,

        @SerializedName("strThumb")
        var strThumb: String? = null,

        @SerializedName("strCutout")
        var strCutout: String? = null
)