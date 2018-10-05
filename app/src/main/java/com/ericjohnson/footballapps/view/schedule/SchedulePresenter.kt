package com.ericjohnson.footballapps.view.schedule

import com.ericjohnson.footballapps.base.BasePresenter
import com.ericjohnson.footballapps.data.api.response.MatchDetailResponse
import com.ericjohnson.footballapps.interactor.GetMatchesInteractor
import com.ericjohnson.footballapps.utils.ScheduleType
import com.ericjohnson.footballapps.view.mainactivity.IMainPresenter
import com.ericjohnson.footballapps.view.mainactivity.MainView

/**
 * Created by johnson on 04/10/18.
 */

class SchedulePresenter<V : ScheduleView> : BasePresenter<V>(),
        ISchedulePresenter<V>, GetMatchesInteractor.GetMatchesInteractorListener {

    private val getMatchesInteractor: GetMatchesInteractor = GetMatchesInteractor(this)

    override fun getMatches(scheduleType: String) {
        getMatchesInteractor.scheduleType = scheduleType
        getMatchesInteractor.call()
    }

    override fun onGetMatchesSuccess(matchDetailResponse: MatchDetailResponse) {
        view?.showSwipeRefresh(false)
        view?.setMatchSchedule(matchDetailResponse.events)
    }

    override fun onGetMatchesFailed() {
        view?.showSwipeRefresh(false)
        view?.showError()
    }

}