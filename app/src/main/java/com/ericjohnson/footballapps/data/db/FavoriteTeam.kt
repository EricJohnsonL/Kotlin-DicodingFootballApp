package com.ericjohnson.footballapps.data.db

/**
 * Created by johnson on 10/23/18.
 */
class FavoriteTeam(val id: String, val name: String, val image: String) {

    companion object {
        const val TABLE_FAVORITE_TEAM: String = "TABLE_FAV_TEAM"
        const val ID: String = "ID_"
        const val NAME: String = "NAME"
        const val IMAGE: String = "IMAGE"
    }
}