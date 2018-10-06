package com.ericjohnson.footballapps.view.matchDetail

import com.ericjohnson.footballapps.base.BasePresenter
import com.ericjohnson.footballapps.data.api.response.TeamDetailResponse
import com.ericjohnson.footballapps.domain.GetAwayTeamBadgeInteractor
import com.ericjohnson.footballapps.domain.GetHomeTeamBadgeInteractor

/**
 * Created by johnson on 06/10/18.
 */

class MatchDetailPresenter<V : MatchDetailView> : BasePresenter<V>(), IMatchDetailPresenter<V>,
        GetHomeTeamBadgeInteractor.GetHomeTeamBadgeInteractorListener, GetAwayTeamBadgeInteractor.GetAwayTeamBadgeInteractorListener {

    private val getHomeTeamBadgeInteractor: GetHomeTeamBadgeInteractor = GetHomeTeamBadgeInteractor(this)

    private val getAwayTeamBadgeInteractor: GetAwayTeamBadgeInteractor = GetAwayTeamBadgeInteractor(this)

    //region Home Team Badge
    override fun getHomeTeamBadge(teamId: String) {
        getHomeTeamBadgeInteractor.teamId = teamId
        getHomeTeamBadgeInteractor.call()
    }

    override fun onGetHomeTeamBadgeSuccess(teamDetailResponse: TeamDetailResponse) {
        view?.showHomeTeamBadge(teamDetailResponse.teams[0].strTeamBadge.toString())
    }

    override fun onGetHomeTeamBadgeFailed(message: String) {
        view?.showError(message)
    }
    //endregion

    //region Away Team Badge
    override fun getAwayTeamBadge(teamId: String) {
        getAwayTeamBadgeInteractor.teamId = teamId
        getAwayTeamBadgeInteractor.call()
    }

    override fun onGetAwayTeamBadgeSuccess(teamDetailResponse: TeamDetailResponse) {
        view?.showAwayTeamBadge(teamDetailResponse.teams[0].strTeamBadge.toString())
    }

    override fun onGetAwayTeamBadgeFailed(message: String) {
        view?.showError(message)
    }
    //endregion

}