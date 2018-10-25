package com.ericjohnson.footballapps.view.teamlist

import com.ericjohnson.footballapps.base.IBaseView
import com.ericjohnson.footballapps.data.api.TeamList
import com.ericjohnson.footballapps.data.api.response.LeagueResponse

/**
 * Created by johnson on 10/22/18.
 */
interface TeamListView : IBaseView {

    fun setTeamList(teamList: MutableList<TeamList>)

    fun setLeague(leagueResponse: LeagueResponse)

    fun showError()

    fun showEmptyTeam(isShown: Boolean)

    fun showSwipeRefresh(isShown: Boolean)

    fun showTeamList(isShown: Boolean)

    fun showProgressBar(isShown: Boolean)

    fun showErrorLeague(isShown: Boolean)
}