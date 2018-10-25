package com.ericjohnson.footballapps.view.favoritecontainer.favoriteteam

import android.content.Context
import com.ericjohnson.footballapps.base.IBasePresenter
import com.ericjohnson.footballapps.base.IBaseView

/**
 * Created by johnson on 10/23/18.
 */
interface IFavoriteTeamPresenter <V : IBaseView> : IBasePresenter<V> {

    fun getAllFavTeams(context: Context)
}