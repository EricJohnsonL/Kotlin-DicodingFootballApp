package com.ericjohnson.footballapps.view.search

import com.ericjohnson.footballapps.base.IBaseView
import com.ericjohnson.footballapps.data.api.response.MatchDetailResponse
import com.ericjohnson.footballapps.data.api.response.SearchedMatchResponse
import com.ericjohnson.footballapps.data.api.response.TeamListResponse

/**
 * Created by johnson on 10/24/18.
 */
interface SearchView : IBaseView {

    fun showProgressBar(isShown: Boolean)

    fun showRecyclerView(isShown: Boolean)

    fun showErrorView(isShown: Boolean)

    fun setMatchesResult(searchedMatchResponse: SearchedMatchResponse)

    fun setTeamsResult(teamListResponse: TeamListResponse)
}