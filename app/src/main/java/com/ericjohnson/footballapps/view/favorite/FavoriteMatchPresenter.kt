package com.ericjohnson.footballapps.view.favorite

import android.content.Context
import com.ericjohnson.footballapps.base.BasePresenter
import com.ericjohnson.footballapps.data.db.FavoriteMatch

/**
 * Created by johnson on 10/10/18.
 */
class FavoriteMatchPresenter<V : FavoriteMatchView> : BasePresenter<V>(), IFavoriteMatchPresenter<V> {

    override fun getAllFavMatches(context: Context) {
        val favoriteMatch: MutableList<FavoriteMatch> = getInteractor().getAllFavMatches(context)
        if (favoriteMatch.isEmpty()) {
            view?.showEmptyFavMatches()
        } else {
            view?.showAllFavMatches(favoriteMatch)
        }
    }
}