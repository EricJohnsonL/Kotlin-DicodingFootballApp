package com.ericjohnson.footballapps.domain

import com.ericjohnson.footballapps.data.Interactor
import com.ericjohnson.footballapps.data.api.response.TeamDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by johnson on 06/10/18.
 */

class GetAwayTeamBadgeInteractor(private val getAwayTeamBadgeInteractorListener: GetAwayTeamBadgeInteractorListener) : Callback<TeamDetailResponse> {

    private fun getInteractor(): Interactor = Interactor()

    lateinit var teamId: String

    fun call() = getInteractor().getTeamDetail(teamId).enqueue(this)

    override fun onResponse(call: Call<TeamDetailResponse>, response: Response<TeamDetailResponse>) {
        if (response.isSuccessful) {
            response.body()?.let { getAwayTeamBadgeInteractorListener.onGetAwayTeamBadgeSuccess(it) }
        } else {
            getAwayTeamBadgeInteractorListener.onGetAwayTeamBadgeFailed(response.message())
        }
    }

    override fun onFailure(call: Call<TeamDetailResponse>, t: Throwable) {
        getAwayTeamBadgeInteractorListener.onGetAwayTeamBadgeFailed(t.message.toString())
    }

    interface GetAwayTeamBadgeInteractorListener {

        fun onGetAwayTeamBadgeSuccess(teamDetailResponse: TeamDetailResponse)

        fun onGetAwayTeamBadgeFailed(message: String)
    }
}