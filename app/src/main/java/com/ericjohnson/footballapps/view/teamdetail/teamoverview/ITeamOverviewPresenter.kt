package com.ericjohnson.footballapps.view.teamdetail.teamoverview

import com.ericjohnson.footballapps.base.IBasePresenter
import com.ericjohnson.footballapps.base.IBaseView

/**
 * Created by johnson on 10/23/18.
 */
interface ITeamOverviewPresenter <V : IBaseView> : IBasePresenter<V> {

    fun getTeamOverview(teamId:String)
}