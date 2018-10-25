package com.ericjohnson.footballapps.data.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by johnson on 10/22/18.
 */
@Parcelize
data class TeamList(

        @SerializedName("idTeam")
        var idTeam: String,

        @SerializedName("strTeam")
        var strTeam: String,

        @SerializedName("strTeamBadge")
        var strTeamBadge: String? = null,

        @SerializedName("strSport")
        var strSport: String? = null

) : Parcelable