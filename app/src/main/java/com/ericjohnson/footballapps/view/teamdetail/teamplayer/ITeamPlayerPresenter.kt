package com.ericjohnson.footballapps.view.teamdetail.teamplayer

import com.ericjohnson.footballapps.base.IBasePresenter
import com.ericjohnson.footballapps.base.IBaseView

/**
 * Created by johnson on 10/23/18.
 */
interface ITeamPlayerPresenter<V : IBaseView> : IBasePresenter<V> {

    fun getPlayers(teamId: String)
}