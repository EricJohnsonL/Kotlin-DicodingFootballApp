package com.ericjohnson.footballapps.domain

import com.ericjohnson.footballapps.data.Interactor
import com.ericjohnson.footballapps.data.api.response.PlayerResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by johnson on 10/23/18.
 */
class GetPlayersFromTeamInteractor(private val getTeamPlayersInteractorListener: GetTeamPlayersInteractorListener) : Callback<PlayerResponse> {

    private fun getInteractor(): Interactor = Interactor()

    lateinit var teamId: String

    fun call() = getInteractor().getPlayerByTeam(teamId).enqueue(this)

    override fun onResponse(call: Call<PlayerResponse>, response: Response<PlayerResponse>) {
        if (response.isSuccessful) {
            response.body()?.let { getTeamPlayersInteractorListener.onGetTeamPlayersInteractorSuccess(it) }
        } else {
            getTeamPlayersInteractorListener.onGetTeamPlayersIntreactorFailed()
        }
    }

    override fun onFailure(call: Call<PlayerResponse>, t: Throwable) {
        getTeamPlayersInteractorListener.onGetTeamPlayersIntreactorFailed()
    }


    interface GetTeamPlayersInteractorListener {

        fun onGetTeamPlayersInteractorSuccess(playerResponse: PlayerResponse)

        fun onGetTeamPlayersIntreactorFailed()
    }
}