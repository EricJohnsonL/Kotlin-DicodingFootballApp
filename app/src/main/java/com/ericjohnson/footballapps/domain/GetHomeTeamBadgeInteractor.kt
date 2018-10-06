package com.ericjohnson.footballapps.domain

import com.ericjohnson.footballapps.data.Interactor
import com.ericjohnson.footballapps.data.api.response.TeamDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by johnson on 06/10/18.
 */

class GetHomeTeamBadgeInteractor(private val getHomeTeamBadgeInteractorListener: GetHomeTeamBadgeInteractorListener) : Callback<TeamDetailResponse> {

    private fun getInteractor(): Interactor = Interactor()

    lateinit var teamId: String

    fun call() = getInteractor().getTeamDetail(teamId).enqueue(this)

    override fun onResponse(call: Call<TeamDetailResponse>, response: Response<TeamDetailResponse>) {
        if (response.isSuccessful) {
            response.body()?.let { getHomeTeamBadgeInteractorListener.onGetHomeTeamBadgeSuccess(it) }
        } else {
            getHomeTeamBadgeInteractorListener.onGetHomeTeamBadgeFailed(response.message())
        }
    }

    override fun onFailure(call: Call<TeamDetailResponse>, t: Throwable) {
        getHomeTeamBadgeInteractorListener.onGetHomeTeamBadgeFailed(t.message.toString())
    }

    interface GetHomeTeamBadgeInteractorListener {

        fun onGetHomeTeamBadgeSuccess(teamDetailResponse: TeamDetailResponse)

        fun onGetHomeTeamBadgeFailed(message: String)
    }
}