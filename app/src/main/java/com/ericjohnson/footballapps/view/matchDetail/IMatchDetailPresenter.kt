package com.ericjohnson.footballapps.view.matchDetail

import android.content.Context
import com.ericjohnson.footballapps.base.IBasePresenter
import com.ericjohnson.footballapps.base.IBaseView
import com.ericjohnson.footballapps.data.db.FavoriteMatch

/**
 * Created by johnson on 06/10/18.
 */
interface IMatchDetailPresenter<V : IBaseView> : IBasePresenter<V> {

    fun getHomeTeamBadge(teamId: String)

    fun getAwayTeamBadge(teamId: String)

    fun getMatchDetail(eventId: String)

    fun checkFavorites(context: Context, id: String)

    fun setToFavorites(context: Context, favoriteMatch: FavoriteMatch)

    fun removeFromFavorites(context: Context, id: String)
}