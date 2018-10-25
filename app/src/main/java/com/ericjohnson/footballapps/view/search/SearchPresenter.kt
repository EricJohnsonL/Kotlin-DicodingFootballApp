package com.ericjohnson.footballapps.view.search

import android.support.v4.content.ContextCompat
import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.base.BasePresenter
import com.ericjohnson.footballapps.data.api.MatchDetail
import com.ericjohnson.footballapps.data.api.TeamList
import com.ericjohnson.footballapps.data.api.response.SearchedMatchResponse
import com.ericjohnson.footballapps.data.api.response.TeamListResponse
import com.ericjohnson.footballapps.domain.GetSearchedMatchesInteractor
import com.ericjohnson.footballapps.domain.GetSearchedTeamsInteractor
import com.ericjohnson.footballapps.utils.AppConstants

/**
 * Created by johnson on 10/24/18.
 */
class SearchPresenter<V : SearchView> : BasePresenter<V>(),
        ISearchPresenter<V>, GetSearchedTeamsInteractor.GetSearchedTeamsInteractorListener,
        GetSearchedMatchesInteractor.GetSearchedMatchesListener {

    private val getSearchedTeamsInteractor: GetSearchedTeamsInteractor = GetSearchedTeamsInteractor(this)

    private val getSearchedMatchesInteractor: GetSearchedMatchesInteractor = GetSearchedMatchesInteractor(this)

    //region Teams
    override fun getTeam(query: String) {
        view?.showProgressBar(true)
        view?.showRecyclerView(false)
        view?.showErrorView(false)
        getSearchedTeamsInteractor.teamName = query
        getSearchedTeamsInteractor.call()
    }

    override fun onGetSearchedTeamsInteractorSuccess(teamListResponse: TeamListResponse) {
        view?.showProgressBar(false)
        view?.showRecyclerView(true)

        val teamsResult: MutableList<TeamList> = mutableListOf()
        if (teamListResponse.teams != null) {
            for (teamResult: TeamList in teamListResponse.teams) {
                if (teamResult.strSport == AppConstants.SOCCER) {
                    teamsResult.add(teamResult)
                }
            }
        }
        val teamResultResponse = TeamListResponse(teamsResult)
        view?.setTeamsResult(teamResultResponse)
    }

    override fun onGetSearchedTeamsIntreactorFailed() {
        view?.showProgressBar(false)
        view?.showErrorView(true)
    }
    //endregion

    //region Matches
    override fun getMathes(query: String) {
        view?.showProgressBar(true)
        view?.showRecyclerView(false)
        view?.showErrorView(false)
        getSearchedMatchesInteractor.query = query
        getSearchedMatchesInteractor.call()
    }

    override fun onGetSearchedMatchesSuccess(searchedMatchResponse: SearchedMatchResponse) {
        view?.showProgressBar(false)
        view?.showRecyclerView(true)

        val matchResults: MutableList<MatchDetail> = mutableListOf()

        if (searchedMatchResponse.event != null) {
            for (matchResult: MatchDetail in searchedMatchResponse.event) {
                if (matchResult.strSport.toString() == AppConstants.SOCCER) {
                    matchResults.add(matchResult)
                }
            }
        }
        val searchedMatchResult = SearchedMatchResponse(matchResults)
        view?.setMatchesResult(searchedMatchResult)
    }

    override fun onGetSearchedMatchesFailed() {
        view?.showProgressBar(false)
        view?.showErrorView(true)
    }
    //endregion
}