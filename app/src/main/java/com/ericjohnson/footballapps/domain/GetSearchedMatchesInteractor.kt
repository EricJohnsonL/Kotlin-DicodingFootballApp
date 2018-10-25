package com.ericjohnson.footballapps.domain

import com.ericjohnson.footballapps.data.Interactor
import com.ericjohnson.footballapps.data.api.response.SearchedMatchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by johnson on 10/24/18.
 */
class GetSearchedMatchesInteractor(private val getSearchedMatchesListener: GetSearchedMatchesListener) : Callback<SearchedMatchResponse> {

    private fun getInteractor(): Interactor = Interactor()

    lateinit var query: String

    fun call() = getInteractor().getSearchedMatches(query).enqueue(this)

    override fun onResponse(call: Call<SearchedMatchResponse>, response: Response<SearchedMatchResponse>) {
        if (response.isSuccessful) {
            response.body()?.let { getSearchedMatchesListener.onGetSearchedMatchesSuccess(it) }
        } else {
            getSearchedMatchesListener.onGetSearchedMatchesFailed()
        }
    }

    override fun onFailure(call: Call<SearchedMatchResponse>, t: Throwable) {
        getSearchedMatchesListener.onGetSearchedMatchesFailed()
    }

    interface GetSearchedMatchesListener {

        fun onGetSearchedMatchesSuccess(searchedMatchResponse: SearchedMatchResponse)

        fun onGetSearchedMatchesFailed()
    }
}