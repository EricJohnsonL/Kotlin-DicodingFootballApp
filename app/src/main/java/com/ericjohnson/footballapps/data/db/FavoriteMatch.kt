package com.ericjohnson.footballapps.data.db

/**
 * Created by johnson on 10/10/18.
 */

data class FavoriteMatch(val id: String?, val date: String?, val homeTeam: String?,
                         val awayTeam: String?, val homeScore: String?, val awayScore: String?){
    companion object {
        const val TABLE_FAVORITE_MATCH:String="TABLE_FAV_MATCH"
        const val ID:String="ID_"
        const val DATE:String="DATE"
        const val HOME_TEAM:String="HOME_TEAM"
        const val AWAY_TEAM:String="AWAY_TEAM"
        const val HOME_SCORE:String="HOME_SCORE"
        const val AWAY_SCORE:String="AWAY_SCORE"
    }
}