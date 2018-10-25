package com.ericjohnson.footballapps.view.playerdetail

import com.ericjohnson.footballapps.base.BasePresenter
import com.ericjohnson.footballapps.data.api.response.PlayerDetailResponse
import com.ericjohnson.footballapps.domain.GetPlayerDetailInteractor

/**
 * Created by johnson on 10/23/18.
 */
class PlayerDetailPresenter<V : PlayerDetailView> : BasePresenter<V>(),
        IPlayerDetailPresenter<V>, GetPlayerDetailInteractor.GetPlayerDetailInteractorListener {

    private val getPlayerDetailInteractor: GetPlayerDetailInteractor = GetPlayerDetailInteractor(this)

    override fun getPlayerDetail(playerId: String) {
        view?.showProgressBar(true)
        view?.showPlayerDetail(false)
        view?.showErrorView(false)
        getPlayerDetailInteractor.playerId = playerId
        getPlayerDetailInteractor.call()
    }

    override fun onGetPlayerDetailInteractorSuccess(playerDetailResponse: PlayerDetailResponse) {
        view?.showProgressBar(false)
        view?.showPlayerDetail(true)
        view?.setPlayerDetail(playerDetailResponse.player[0])
    }

    override fun onGetPlayerDetailInteractorFailed() {
        view?.showProgressBar(false)
        view?.showErrorView(true)
    }
}