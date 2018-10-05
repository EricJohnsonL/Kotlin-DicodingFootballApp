package com.ericjohnson.footballapps.view.schedule

import com.ericjohnson.footballapps.base.IBasePresenter
import com.ericjohnson.footballapps.base.IBaseView
import com.ericjohnson.footballapps.utils.ScheduleType

/**
 * Created by johnson on 04/10/18.
 */
interface ISchedulePresenter<V : IBaseView> : IBasePresenter<V> {

    fun getMatches(scheduleType: String)
}