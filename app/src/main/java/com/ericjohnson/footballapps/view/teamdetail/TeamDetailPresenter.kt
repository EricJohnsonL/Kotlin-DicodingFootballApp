package com.ericjohnson.footballapps.view.teamdetail

import android.content.Context
import com.ericjohnson.footballapps.base.BasePresenter
import com.ericjohnson.footballapps.data.db.FavoriteTeam


/**
 * Created by johnson on 10/22/18.
 */
class TeamDetailPresenter<V : TeamDetailView> : BasePresenter<V>(), ITeamDetailPresenter<V> {

    override fun checkFavorites(context: Context, id: String) {
        if (getInteractor().getFavTeam(context, id).isEmpty()) {
            view?.checkIsFavorite(false)
        } else {
            view?.checkIsFavorite(true)
        }
    }

    override fun setToFavorites(context: Context, favoriteTeam: FavoriteTeam) {
        if (getInteractor().insertFavTeam(context, favoriteTeam)) {
            view?.addTofavoriteSuccess()
        } else {
            view?.addOrRemoveFavoriteFailed()
        }
    }

    override fun removeFromFavorites(context: Context, id: String) {
        if (getInteractor().removeFavTeam(context, id)) {
            view?.removeFromFavoriteSuccess()
        } else {
            view?.addOrRemoveFavoriteFailed()
        }
    }
}