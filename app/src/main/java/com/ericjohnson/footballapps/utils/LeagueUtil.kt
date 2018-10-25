package com.ericjohnson.footballapps.utils

import android.content.Context
import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.data.api.LeagueManual

/**
 * Created by johnson on 10/19/18.
 */

class LeagueUtil {

    companion object {
        fun getLeague(context: Context): List<LeagueManual> {

            val leagueId: List<String> = context.resources.getStringArray(R.array.league_id).toList()
            val leagueName: List<String> = context.resources.getStringArray(R.array.league).toList()
            val leagueManual: MutableList<LeagueManual> = mutableListOf()

            (0 until leagueId.size).forEach { i ->
                leagueManual.add(LeagueManual(leagueId[i], leagueName[i]))
            }

            return leagueManual
        }
    }
}