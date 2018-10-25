package com.ericjohnson.footballapps.data.db.setup

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.ericjohnson.footballapps.data.db.FavoriteMatch
import com.ericjohnson.footballapps.data.db.FavoriteTeam
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
                        FavoriteMatch.TIME to favoriteMatch.time,
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

    override fun getAllFavTeams(context: Context): MutableList<FavoriteTeam> {
        var favoriteList: List<FavoriteTeam> = listOf()
        DatabaseOpenHelper.getInstance(context).use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
                    .orderBy(FavoriteTeam.NAME, SqlOrderDirection.ASC)
            favoriteList = result.parseList(classParser())
        }
        return favoriteList.toMutableList()
    }

    override fun getFavTeam(context: Context, id: String): MutableList<FavoriteTeam> {
        var favoriteList: List<FavoriteTeam> = listOf()
        DatabaseOpenHelper.getInstance(context).use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
                    .whereArgs("(ID_ = {id})",
                            "id" to id)
            favoriteList = result.parseList(classParser())
        }
        return favoriteList.toMutableList()

    }

    override fun insertFavTeam(context: Context, favoriteTeam: FavoriteTeam): Boolean {
        return try {
            DatabaseOpenHelper.getInstance(context).use {
                insert(FavoriteTeam.TABLE_FAVORITE_TEAM,
                        FavoriteTeam.ID to favoriteTeam.id,
                        FavoriteTeam.NAME to favoriteTeam.name,
                        FavoriteTeam.IMAGE to favoriteTeam.image)
            }
            true
        } catch (e: SQLiteConstraintException) {
            Log.d(DbRepository::class.java.simpleName, e.localizedMessage)
            false
        }
    }

    override fun removeFavTeam(context: Context, id: String): Boolean {
        return try {
            DatabaseOpenHelper.getInstance(context).use {
                delete(FavoriteTeam.TABLE_FAVORITE_TEAM,
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