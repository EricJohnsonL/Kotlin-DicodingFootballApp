package com.ericjohnson.footballapps.view.teamdetail.teamplayer

import com.ericjohnson.footballapps.base.BasePresenter
import com.ericjohnson.footballapps.data.api.response.PlayerResponse
import com.ericjohnson.footballapps.domain.GetPlayersFromTeamInteractor

/**
 * Created by johnson on 10/23/18.
 */

class TeamPlayerPresenter<V : TeamPlayerView> : BasePresenter<V>(),
        ITeamPlayerPresenter<V>, GetPlayersFromTeamInteractor.GetTeamPlayersInteractorListener {

    private val getPlayersFromTeamInteractor: GetPlayersFromTeamInteractor = GetPlayersFromTeamInteractor(this)

    override fun getPlayers(teamId: String) {
        view?.showProgressBar(true)
        view?.showPlayers(false)
        view?.showError(false)
        view?.showEmptyData(false)
        getPlayersFromTeamInteractor.teamId = teamId
        getPlayersFromTeamInteractor.call()
    }

    override fun onGetTeamPlayersInteractorSuccess(playerResponse: PlayerResponse) {
        view?.showProgressBar(false)
        if (playerResponse.player == null) {
            view?.showEmptyData(true)
        } else {
            view?.showPlayers(true)
            view?.setPlayers(playerResponse)
        }
    }

    override fun onGetTeamPlayersIntreactorFailed() {
        view?.showProgressBar(false)
        view?.showError(true)
    }


}