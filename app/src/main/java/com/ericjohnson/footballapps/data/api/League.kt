package com.ericjohnson.footballapps.data.api

import com.google.gson.annotations.SerializedName

/**
 * Created by johnson on 10/24/18.
 */
data class League(

        @SerializedName("idLeague")
        var idLeague: String,

        @SerializedName("strLeague")
        var strLeague: String,

        @SerializedName("strSport")
        var strSport: String
) {
    override fun toString(): String {
        return strLeague
    }
}