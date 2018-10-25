package com.ericjohnson.footballapps.view.favoritecontainer.favoriteteam

import android.content.Context
import com.ericjohnson.footballapps.base.BasePresenter
import com.ericjohnson.footballapps.data.db.FavoriteTeam
import com.ericjohnson.footballapps.view.favoritecontainer.favoritematch.IFavoriteMatchPresenter

/**
 * Created by johnson on 10/23/18.
 */
class FavoriteTeamPresenter<V : FavoriteTeamView> : BasePresenter<V>(), IFavoriteTeamPresenter<V> {
    override fun getAllFavTeams(context: Context) {
        val favoriteTeam: MutableList<FavoriteTeam> = getInteractor().getAllFavTeams(context)
        if (favoriteTeam.isEmpty()) {
            view?.showEmptyFavTeams()
        } else {
            view?.showAllFavTeams(favoriteTeam)
        }
    }
}