package com.ericjohnson.footballapps.domain

import com.ericjohnson.footballapps.data.Interactor
import com.ericjohnson.footballapps.data.api.response.PlayerDetailResponse
import com.ericjohnson.footballapps.data.api.response.PlayerResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by johnson on 10/23/18.
 */
class GetPlayerDetailInteractor(private val getPlayerDetailInteractorListener: GetPlayerDetailInteractorListener) : Callback<PlayerDetailResponse> {

    private fun getInteractor(): Interactor = Interactor()

    lateinit var playerId: String

    fun call() = getInteractor().getPlayerDetail(playerId).enqueue(this)

    override fun onResponse(call: Call<PlayerDetailResponse>, response: Response<PlayerDetailResponse>) {
        if (response.isSuccessful) {
            response.body()?.let { getPlayerDetailInteractorListener.onGetPlayerDetailInteractorSuccess(it) }
        } else {
            getPlayerDetailInteractorListener.onGetPlayerDetailInteractorFailed()
        }
    }

    override fun onFailure(call: Call<PlayerDetailResponse>, t: Throwable) {
        getPlayerDetailInteractorListener.onGetPlayerDetailInteractorFailed()
    }


    interface GetPlayerDetailInteractorListener {

        fun onGetPlayerDetailInteractorSuccess(playerDetailResponse: PlayerDetailResponse)

        fun onGetPlayerDetailInteractorFailed()
    }
}