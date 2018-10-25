package com.ericjohnson.footballapps.domain

import com.ericjohnson.footballapps.data.Interactor
import com.ericjohnson.footballapps.data.api.response.TeamListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by johnson on 10/22/18.
 */

class GetTeamListInteractor(private val getTeamListInteractorListener: GetTeamListInteractorListener) : Callback<TeamListResponse> {

    private fun getInteractor(): Interactor = Interactor()

    lateinit var leagueId: String

    fun call() = getInteractor().getTeamList(leagueId).enqueue(this)

    override fun onResponse(call: Call<TeamListResponse>, response: Response<TeamListResponse>) {
        if (response.isSuccessful) {
            response.body()?.let { getTeamListInteractorListener.onGetTeamListInteractorSuccess(it) }
        } else {
            getTeamListInteractorListener.onGetTeamListIntreactorFailed()
        }
    }

    override fun onFailure(call: Call<TeamListResponse>, t: Throwable) {
        getTeamListInteractorListener.onGetTeamListIntreactorFailed()
    }

    interface GetTeamListInteractorListener {

        fun onGetTeamListInteractorSuccess(teamListResponse: TeamListResponse)

        fun onGetTeamListIntreactorFailed()
    }

}