package com.ericjohnson.footballapps.view.teamdetail.teamoverview

import com.ericjohnson.footballapps.base.BasePresenter
import com.ericjohnson.footballapps.data.api.response.TeamDetailResponse
import com.ericjohnson.footballapps.domain.GetHomeTeamDetailInteractor

/**
 * Created by johnson on 10/23/18.
 */
class TeamOverviewPresenter<V : TeamOverviewView> : BasePresenter<V>(),
        ITeamOverviewPresenter<V>, GetHomeTeamDetailInteractor.GetHomeTeamDetailInteractorListener {

    private val getHomeTeamDetailInteractor: GetHomeTeamDetailInteractor = GetHomeTeamDetailInteractor(this)

    override fun getTeamOverview(teamId: String) {
        view?.showProgressBar(true)
        view?.showTeamOverview(false)
        view?.showError(false)
        getHomeTeamDetailInteractor.teamId = teamId
        getHomeTeamDetailInteractor.call()
    }

    override fun onGetHomeTeamDetailSuccess(teamDetailResponse: TeamDetailResponse) {
        view?.showProgressBar(false)
        view?.showTeamOverview(true)
        view?.setTeamOverview(teamDetailResponse.teams[0])
    }

    override fun onGetHomeTeamDetailFailed(message: String) {
        view?.showProgressBar(false)
        view?.showError(true)
    }
}