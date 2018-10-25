package com.ericjohnson.footballapps.view.matchescontainer.schedule

import com.ericjohnson.footballapps.base.IBasePresenter
import com.ericjohnson.footballapps.base.IBaseView

/**
 * Created by johnson on 04/10/18.
 */
interface ISchedulePresenter<V : IBaseView> : IBasePresenter<V> {

    fun getMatches(scheduleType: String, leagueId: String)
}