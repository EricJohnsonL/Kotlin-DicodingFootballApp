package com.ericjohnson.footballapps.view.matchescontainer.schedule

import com.ericjohnson.footballapps.base.IBaseView
import com.ericjohnson.footballapps.data.api.MatchDetail

/**
 * Created by johnson on 04/10/18.
 */

interface ScheduleView : IBaseView{

    fun setMatchSchedule(matchSchedule:MutableList<MatchDetail>)

    fun showError()

    fun showSwipeRefresh(isShown:Boolean)

    fun showEmptyView()

}