package com.ericjohnson.footballapps.view.playerdetail

import com.ericjohnson.footballapps.base.IBaseView
import com.ericjohnson.footballapps.data.api.PlayerDetail

/**
 * Created by johnson on 10/23/18.
 */
interface PlayerDetailView : IBaseView {

    fun showProgressBar(isShown: Boolean)

    fun showPlayerDetail(isShown: Boolean)

    fun setPlayerDetail(playerDetail: PlayerDetail)

    fun showErrorView(isShown: Boolean)

}