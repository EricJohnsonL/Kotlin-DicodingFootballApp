package com.ericjohnson.footballapps.view.matchescontainer.schedule

import com.ericjohnson.footballapps.base.BasePresenter
import com.ericjohnson.footballapps.data.api.response.MatchDetailResponse
import com.ericjohnson.footballapps.domain.GetMatchesInteractor

/**
 * Created by johnson on 04/10/18.
 */

class SchedulePresenter<V : ScheduleView> : BasePresenter<V>(),
        ISchedulePresenter<V>, GetMatchesInteractor.GetMatchesInteractorListener {

    private val getMatchesInteractor: GetMatchesInteractor = GetMatchesInteractor(this)

    override fun getMatches(scheduleType: String, leagueId: String) {
        getMatchesInteractor.scheduleType = scheduleType
        getMatchesInteractor.leagueId = leagueId
        getMatchesInteractor.call()
    }

    override fun onGetMatchesSuccess(matchDetailResponse: MatchDetailResponse) {
        view?.showSwipeRefresh(false)
        if (matchDetailResponse.events == null) {
            view?.showEmptyView()
        } else {
            view?.setMatchSchedule(matchDetailResponse.events)
        }
    }

    override fun onGetMatchesFailed() {
        view?.showSwipeRefresh(false)
        view?.showError()
    }

}