package com.ericjohnson.footballapps.view.teamlist

import com.ericjohnson.footballapps.base.BasePresenter
import com.ericjohnson.footballapps.data.api.League
import com.ericjohnson.footballapps.data.api.response.LeagueResponse
import com.ericjohnson.footballapps.data.api.response.TeamListResponse
import com.ericjohnson.footballapps.domain.GetLeagueInteractor
import com.ericjohnson.footballapps.domain.GetTeamListInteractor
import com.ericjohnson.footballapps.utils.AppConstants

/**
 * Created by johnson on 10/22/18.
 */
class TeamListPresenter<V : TeamListView> : BasePresenter<V>(), ITeamListPresenter<V>, GetTeamListInteractor.GetTeamListInteractorListener, GetLeagueInteractor.GetLeagueInteractorListener {

    private val getTeamListInteractor: GetTeamListInteractor = GetTeamListInteractor(this)

    private val getLeagueInteractor: GetLeagueInteractor = GetLeagueInteractor(this)

    override fun getLeague() {
        view?.showProgressBar(true)
        view?.showErrorLeague(false)
        view?.showTeamList(false)
        getLeagueInteractor.call()
    }

    override fun onGetLeagueInteractorSuccess(leagueResponse: LeagueResponse) {
        view?.showProgressBar(false)
        view?.showTeamList(true)

        val leagueList: MutableList<League> = mutableListOf()
        for (league: League in leagueResponse.league) {
            if (league.strSport == AppConstants.SOCCER && league.strLeague != AppConstants.NO_LEAGUE) {
                leagueList.add(league)
            }
        }
        val leagueResult = LeagueResponse(leagueList)
        view?.setLeague(leagueResult)
    }

    override fun onGetLeagueInteractorFailed() {
        view?.showProgressBar(false)
        view?.showErrorLeague(true)
    }


    override fun getTeamList(leagueId: String) {
        getTeamListInteractor.leagueId = leagueId
        getTeamListInteractor.call()
    }

    override fun onGetTeamListInteractorSuccess(teamListResponse: TeamListResponse) {
        view?.showSwipeRefresh(false)
        if (teamListResponse.teams == null) {
            view?.showEmptyTeam(true)
        } else {
            view?.setTeamList(teamListResponse.teams)
        }
    }

    override fun onGetTeamListIntreactorFailed() {
        view?.showSwipeRefresh(false)
        view?.showError()
    }
}