package com.ericjohnson.footballapps.view.favoritecontainer.favoriteteam

import com.ericjohnson.footballapps.base.IBaseView
import com.ericjohnson.footballapps.data.db.FavoriteMatch
import com.ericjohnson.footballapps.data.db.FavoriteTeam

/**
 * Created by johnson on 10/23/18.
 */
interface FavoriteTeamView : IBaseView {

    fun showAllFavTeams(favoriteTeam: MutableList<FavoriteTeam>)

    fun showEmptyFavTeams()
}