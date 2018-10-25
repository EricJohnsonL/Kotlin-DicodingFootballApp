package com.ericjohnson.footballapps.data.db.setup

import android.content.Context
import com.ericjohnson.footballapps.data.db.FavoriteMatch
import com.ericjohnson.footballapps.data.db.FavoriteTeam

/**
 * Created by johnson on 10/10/18.
 */
interface IDbRepository {

    fun getAllFavMatches(context: Context): MutableList<FavoriteMatch>

    fun getFavMatch(context: Context, id: String): MutableList<FavoriteMatch>

    fun insertFavMatch(context: Context, favoriteMatch: FavoriteMatch): Boolean

    fun removeFavMatch(context: Context, id: String): Boolean

    fun getAllFavTeams(context: Context): MutableList<FavoriteTeam>

    fun getFavTeam(context: Context, id: String): MutableList<FavoriteTeam>

    fun insertFavTeam(context: Context, favoriteTeam: FavoriteTeam): Boolean

    fun removeFavTeam(context: Context, id: String): Boolean
}