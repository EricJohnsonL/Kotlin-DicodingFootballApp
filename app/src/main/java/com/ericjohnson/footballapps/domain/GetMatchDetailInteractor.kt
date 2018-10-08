package com.ericjohnson.footballapps.domain

import com.ericjohnson.footballapps.data.Interactor
import com.ericjohnson.footballapps.data.api.MatchDetail
import com.ericjohnson.footballapps.data.api.response.MatchDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by johnson on 08/10/18.
 */

class GetMatchDetailInteractor(private val getMatchDetailInteractorListener: GetMatchDetailInteractorListener) : Callback<MatchDetailResponse> {

    private fun getInteractor(): Interactor = Interactor()

    lateinit var eventId: String

    fun call() = getInteractor().getMatchDetail(eventId).enqueue(this)

    override fun onResponse(call: Call<MatchDetailResponse>, response: Response<MatchDetailResponse>) {
        if (response.isSuccessful) {
            response.body()?.let { getMatchDetailInteractorListener.onGetMatchDetailSuccess(it.events[0]) }
        } else {
            getMatchDetailInteractorListener.onGetMatchDetailFailed()
        }

    }

    override fun onFailure(call: Call<MatchDetailResponse>, t: Throwable) {
        getMatchDetailInteractorListener.onGetMatchDetailFailed()

    }

    interface GetMatchDetailInteractorListener {

        fun onGetMatchDetailSuccess(matchDetail: MatchDetail)

        fun onGetMatchDetailFailed()
    }

}