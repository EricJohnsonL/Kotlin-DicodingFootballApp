package com.ericjohnson.footballapps.view.matchDetail

import com.ericjohnson.footballapps.base.IBasePresenter
import com.ericjohnson.footballapps.base.IBaseView

/**
 * Created by johnson on 06/10/18.
 */
interface IMatchDetailPresenter<V : IBaseView> : IBasePresenter<V> {

    fun getHomeTeamBadge(teamId: String)

    fun getAwayTeamBadge(teamId: String)

    fun getMatchDetail(eventId:String)

}