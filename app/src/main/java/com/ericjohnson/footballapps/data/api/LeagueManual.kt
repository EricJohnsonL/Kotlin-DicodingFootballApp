package com.ericjohnson.footballapps.data.api

/**
 * Created by johnson on 10/19/18.
 */
data class LeagueManual(var leagueId: String, var leagueName: String) {

    override fun toString(): String {
        return leagueName
    }
}