package com.ericjohnson.footballapps.domain

import com.ericjohnson.footballapps.data.Interactor
import com.ericjohnson.footballapps.data.api.response.LeagueResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by johnson on 10/24/18.
 */
class GetLeagueInteractor(val getLeagueInteractorListener: GetLeagueInteractorListener) : Callback<LeagueResponse> {

    private fun getInteractor(): Interactor = Interactor()

    fun call() = getInteractor().getLeague().enqueue(this)

    override fun onResponse(call: Call<LeagueResponse>, response: Response<LeagueResponse>) {
        if (response.isSuccessful) {
            response.body()?.let { getLeagueInteractorListener.onGetLeagueInteractorSuccess(it) }
        } else {
            getLeagueInteractorListener.onGetLeagueInteractorFailed()
        }
    }

    override fun onFailure(call: Call<LeagueResponse>, t: Throwable) {
        getLeagueInteractorListener.onGetLeagueInteractorFailed()
    }

    interface GetLeagueInteractorListener {

        fun onGetLeagueInteractorSuccess(leagueResponse: LeagueResponse)

        fun onGetLeagueInteractorFailed()
    }
}