package com.ericjohnson.footballapps.view.matchescontainer

import com.ericjohnson.footballapps.base.BasePresenter
import com.ericjohnson.footballapps.data.api.League
import com.ericjohnson.footballapps.data.api.response.LeagueResponse
import com.ericjohnson.footballapps.domain.GetLeagueInteractor
import com.ericjohnson.footballapps.utils.AppConstants

/**
 * Created by johnson on 10/18/18.
 */
class MatchesContainerPresenter<V : MatchesContainerView> : BasePresenter<V>(),
        IMatchesContainerPresenter<V>, GetLeagueInteractor.GetLeagueInteractorListener {

    private val getLeagueInteractor: GetLeagueInteractor = GetLeagueInteractor(this)

    override fun getLeague() {
        view?.showProgressBar(true)
        view?.showError(false)
        view?.showLayout(false)
        getLeagueInteractor.call()
    }

    override fun onGetLeagueInteractorSuccess(leagueResponse: LeagueResponse) {
        view?.showProgressBar(false)
        view?.showLayout(true)

        val leagueList: MutableList<League> = mutableListOf()
        for (league: League in leagueResponse.league) {
            if (league.strSport == AppConstants.SOCCER && league.strLeague != AppConstants.NO_LEAGUE) {
                leagueList.add(league)
            }
        }
        val leagueResult=LeagueResponse(leagueList)
        view?.setLeague(leagueResult)
    }

    override fun onGetLeagueInteractorFailed() {
        view?.showProgressBar(false)
        view?.showError(true)
    }
}