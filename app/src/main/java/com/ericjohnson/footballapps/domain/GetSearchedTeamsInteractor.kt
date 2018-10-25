package com.ericjohnson.footballapps.domain

import com.ericjohnson.footballapps.data.Interactor
import com.ericjohnson.footballapps.data.api.response.TeamListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by johnson on 10/24/18.
 */
class GetSearchedTeamsInteractor(private val onGetSearchedTeamsInteractorListener: GetSearchedTeamsInteractorListener) : Callback<TeamListResponse> {

    private fun getInteractor(): Interactor = Interactor()

    lateinit var teamName: String

    fun call() = getInteractor().getSearchedTeams(teamName).enqueue(this)

    override fun onResponse(call: Call<TeamListResponse>, response: Response<TeamListResponse>) {
        if (response.isSuccessful) {
            response.body()?.let { onGetSearchedTeamsInteractorListener.onGetSearchedTeamsInteractorSuccess(it) }
        } else {
            onGetSearchedTeamsInteractorListener.onGetSearchedTeamsIntreactorFailed()
        }
    }

    override fun onFailure(call: Call<TeamListResponse>, t: Throwable) {
        onGetSearchedTeamsInteractorListener.onGetSearchedTeamsIntreactorFailed()
    }

    interface GetSearchedTeamsInteractorListener {

        fun onGetSearchedTeamsInteractorSuccess(teamListResponse: TeamListResponse)

        fun onGetSearchedTeamsIntreactorFailed()
    }
}