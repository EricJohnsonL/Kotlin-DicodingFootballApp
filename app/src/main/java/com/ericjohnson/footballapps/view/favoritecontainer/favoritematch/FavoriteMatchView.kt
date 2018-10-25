package com.ericjohnson.footballapps.view.favoritecontainer.favoritematch

import com.ericjohnson.footballapps.base.IBaseView
import com.ericjohnson.footballapps.data.db.FavoriteMatch

/**
 * Created by johnson on 10/10/18.
 */
interface FavoriteMatchView : IBaseView {

    fun showAllFavMatches(favoriteMatches: MutableList<FavoriteMatch>)

    fun showEmptyFavMatches()
}