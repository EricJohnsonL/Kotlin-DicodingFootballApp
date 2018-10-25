package com.ericjohnson.footballapps.view.teamdetail.teamoverview

import com.ericjohnson.footballapps.base.IBaseView
import com.ericjohnson.footballapps.data.api.TeamDetail

/**
 * Created by johnson on 10/23/18.
 */

interface TeamOverviewView : IBaseView {

    fun showProgressBar(isShown: Boolean)

    fun showTeamOverview(isShown: Boolean)

    fun setTeamOverview(teamDetail: TeamDetail)

    fun showError(isShown: Boolean)

}