package com.ericjohnson.footballapps.domain

import com.ericjohnson.footballapps.BuildConfig
import com.ericjohnson.footballapps.data.Interactor
import com.ericjohnson.footballapps.data.api.response.MatchDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by johnson on 05/10/18.
 */

class GetMatchesInteractor(private val getMatchesInteractorListener: GetMatchesInteractorListener) : Callback<MatchDetailResponse> {

    private fun getInteractor(): Interactor = Interactor()

    lateinit var scheduleType: String

    fun call() = getInteractor().getMatches(scheduleType, BuildConfig.LEAGUE_ID).enqueue(this)

    override fun onResponse(call: Call<MatchDetailResponse>, response: Response<MatchDetailResponse>) {
        if (response.isSuccessful) {
            response.body()?.let { getMatchesInteractorListener.onGetMatchesSuccess(it) }
        }
    }

    override fun onFailure(call: Call<MatchDetailResponse>, t: Throwable) {
        getMatchesInteractorListener.onGetMatchesFailed()
    }


    interface GetMatchesInteractorListener {

        fun onGetMatchesSuccess(matchDetailResponse: MatchDetailResponse)

        fun onGetMatchesFailed()
    }
}