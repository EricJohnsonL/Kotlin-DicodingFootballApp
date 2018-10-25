package com.ericjohnson.footballapps.data.db.setup

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.ericjohnson.footballapps.data.db.FavoriteMatch
import com.ericjohnson.footballapps.data.db.FavoriteTeam
import org.jetbrains.anko.db.*

/**
 * Created by johnson on 10/10/18.
 */

class DatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper
(ctx, "Goalmania.db", null, 1) {

    companion object {
        private var instance: DatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseOpenHelper {
            if (instance == null) {
                instance = DatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as DatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(FavoriteMatch.TABLE_FAVORITE_MATCH, true,
                FavoriteMatch.ID to TEXT + PRIMARY_KEY,
                FavoriteMatch.DATE to TEXT,
                FavoriteMatch.TIME to TEXT,
                FavoriteMatch.HOME_TEAM to TEXT,
                FavoriteMatch.AWAY_TEAM to TEXT,
                FavoriteMatch.HOME_SCORE to TEXT,
                FavoriteMatch.AWAY_SCORE to TEXT)

        db.createTable(FavoriteTeam.TABLE_FAVORITE_TEAM, true,
                FavoriteTeam.ID to TEXT + PRIMARY_KEY,
                FavoriteTeam.NAME to TEXT,
                FavoriteTeam.IMAGE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoriteMatch.TABLE_FAVORITE_MATCH, true)
        db.dropTable(FavoriteTeam.TABLE_FAVORITE_TEAM, true)
        onCreate(db)
    }
}