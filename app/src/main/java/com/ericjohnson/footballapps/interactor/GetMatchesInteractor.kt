package com.ericjohnson.footballapps.interactor

import com.ericjohnson.footballapps.BuildConfig
import com.ericjohnson.footballapps.data.api.MatchDetail
import com.ericjohnson.footballapps.data.api.response.MatchDetailResponse
import com.ericjohnson.footballapps.data.api.setup.ApiRepository
import com.ericjohnson.footballapps.data.api.setup.Interactor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by johnson on 05/10/18.
 */

class GetMatchesInteractor(private val getMatchesInteractorListener: GetMatchesInteractorListener) : Callback<MatchDetailResponse> {

    private val getMatches: ApiRepository = Interactor.start()

    lateinit var scheduleType: String

    fun call() = getMatches.getMatches(scheduleType, BuildConfig.LEAGUE_ID).enqueue(this)

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