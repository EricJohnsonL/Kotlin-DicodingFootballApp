package com.ericjohnson.footballapps.data.db.setup

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.ericjohnson.footballapps.data.db.FavoriteMatch
import org.jetbrains.anko.db.*

/**
 * Created by johnson on 10/10/18.
 */

class DbRepository : IDbRepository {

    override fun getAllFavMatches(context: Context): MutableList<FavoriteMatch> {
        var favoriteList: List<FavoriteMatch> = listOf()
        DatabaseOpenHelper.getInstance(context).use {
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
                    .orderBy(FavoriteMatch.DATE, SqlOrderDirection.DESC)
            favoriteList = result.parseList(classParser())
        }
        return favoriteList.toMutableList()
    }

    override fun getFavMatch(context: Context, id: String): MutableList<FavoriteMatch> {
        var favoriteList: List<FavoriteMatch> = listOf()
        DatabaseOpenHelper.getInstance(context).use {
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
                    .whereArgs("(ID_ = {id})",
                            "id" to id)
            favoriteList = result.parseList(classParser())
        }
        return favoriteList.toMutableList()
    }

    override fun insertFavMatch(context: Context, favoriteMatch: FavoriteMatch): Boolean {
        return try {
            DatabaseOpenHelper.getInstance(context).use {
                insert(FavoriteMatch.TABLE_FAVORITE_MATCH,
                        FavoriteMatch.ID to favoriteMatch.id,
                        FavoriteMatch.DATE to favoriteMatch.date,
                        FavoriteMatch.HOME_TEAM to favoriteMatch.homeTeam,
                        FavoriteMatch.AWAY_TEAM to favoriteMatch.awayTeam,
                        FavoriteMatch.HOME_SCORE to favoriteMatch.homeScore,
                        FavoriteMatch.AWAY_SCORE to favoriteMatch.awayScore)
            }
            true
        } catch (e: SQLiteConstraintException) {
            Log.d(DbRepository::class.java.simpleName, e.localizedMessage)
            false
        }
    }

    override fun removeFavMatch(context: Context, id: String): Boolean {
        return try {
            DatabaseOpenHelper.getInstance(context).use {
                delete(FavoriteMatch.TABLE_FAVORITE_MATCH,
                        "(ID_ = {id})",
                        "id" to id)
            }
            true
        } catch (e: SQLiteConstraintException) {
            Log.d(DbRepository::class.java.simpleName, e.localizedMessage)
            false
        }
    }


}