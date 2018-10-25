package com.ericjohnson.footballapps.view.teamlist

import com.ericjohnson.footballapps.base.IBasePresenter
import com.ericjohnson.footballapps.base.IBaseView

/**
 * Created by johnson on 10/22/18.
 */
interface ITeamListPresenter<V : IBaseView> : IBasePresenter<V> {

    fun getTeamList(leagueId: String)

    fun getLeague()
}