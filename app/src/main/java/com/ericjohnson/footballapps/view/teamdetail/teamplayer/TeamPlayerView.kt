package com.ericjohnson.footballapps.view.teamdetail.teamplayer

import com.ericjohnson.footballapps.base.IBaseView
import com.ericjohnson.footballapps.data.api.TeamDetail
import com.ericjohnson.footballapps.data.api.response.PlayerResponse

/**
 * Created by johnson on 10/23/18.
 */
interface TeamPlayerView : IBaseView {

    fun showProgressBar(isShown: Boolean)

    fun showPlayers(isShown: Boolean)

    fun setPlayers(playerResponse: PlayerResponse)

    fun showError(isShown: Boolean)

    fun showEmptyData(isShown: Boolean)
}