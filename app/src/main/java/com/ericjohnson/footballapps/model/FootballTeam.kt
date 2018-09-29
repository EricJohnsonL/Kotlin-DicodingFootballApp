package com.ericjohnson.footballapps.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by johnson on 29/09/18.
 */

@Parcelize
data class FootballTeam(val name: String?, val desc: String?, val image: Int?) : Parcelable