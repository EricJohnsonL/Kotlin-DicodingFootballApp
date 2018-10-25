package com.ericjohnson.footballapps.view.matchescontainer

import com.ericjohnson.footballapps.base.IBaseView
import com.ericjohnson.footballapps.data.api.response.LeagueResponse

/**
 * Created by johnson on 10/18/18.
 */
interface MatchesContainerView : IBaseView{

    fun showProgressBar(isShown: Boolean)

    fun showLayout(isShown: Boolean)

    fun setLeague(leagueResponse: LeagueResponse)

    fun showError(isShown: Boolean)
}