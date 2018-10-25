package com.ericjohnson.footballapps.view.teamdetail

import android.content.Context
import com.ericjohnson.footballapps.base.IBasePresenter
import com.ericjohnson.footballapps.base.IBaseView
import com.ericjohnson.footballapps.data.db.FavoriteTeam

/**
 * Created by johnson on 10/22/18.
 */
interface ITeamDetailPresenter<V : IBaseView> : IBasePresenter<V> {

    fun checkFavorites(context: Context, id: String)

    fun setToFavorites(context: Context, favoriteTeam: FavoriteTeam)

    fun removeFromFavorites(context: Context, id: String)
}