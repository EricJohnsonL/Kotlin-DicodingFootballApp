package com.ericjohnson.footballapps

import com.ericjohnson.footballapps.data.api.MatchDetail
import com.ericjohnson.footballapps.data.api.TeamList
import com.ericjohnson.footballapps.data.api.response.SearchedMatchResponse
import com.ericjohnson.footballapps.data.api.response.TeamListResponse
import com.ericjohnson.footballapps.data.api.setup.ApiRepository
import com.ericjohnson.footballapps.view.search.SearchPresenter
import com.ericjohnson.footballapps.view.search.SearchView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.mock.Calls

/**
 * Created by johnson on 10/25/18.
 */
class SearchPresenterTest {

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var searchView: SearchView

    private lateinit var teamItems: MutableList<TeamList>

    private lateinit var teamResponse: TeamListResponse

    private lateinit var matchItems: MutableList<MatchDetail>

    private lateinit var matchResponse: SearchedMatchResponse

    private lateinit var searchPresenter: SearchPresenter<SearchView>

    private lateinit var query: String

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        searchPresenter = SearchPresenter()
        searchPresenter.onAttach(searchView)
        teamItems = mutableListOf()
        teamResponse = TeamListResponse(teamItems)
        matchItems = mutableListOf()
        matchResponse = SearchedMatchResponse(matchItems)
        query = "Real Madrid"
    }

    @Test
    fun apiRepositoryGetSearchedMatchTest() {
        apiRepository.getSearchedMatches(query)
        Mockito.verify(apiRepository).getSearchedMatches(query)
    }

    @Test
    fun presenterGetSearchedMatchSuccessTest() {
        Mockito.`when`(apiRepository.getSearchedMatches(query)).thenReturn(Calls.response(matchResponse))
        searchPresenter.getMatches(query)
        searchPresenter.onGetSearchedMatchesSuccess(matchResponse)
        Mockito.verify(searchView).showProgressBar(false)
        Mockito.verify(searchView).showRecyclerView(true)
        Mockito.verify(searchView).setMatchesResult(matchResponse)
    }

    @Test
    fun presenterGetSearchedMatchFailedTest() {
        Mockito.`when`(apiRepository.getSearchedMatches(query)).thenReturn(Calls.response(matchResponse))
        searchPresenter.getMatches(query)
        searchPresenter.onGetSearchedMatchesFailed()
        Mockito.verify(searchView).showProgressBar(false)
        Mockito.verify(searchView).showErrorView(true)
    }

    @Test
    fun apiRepositoryGetSearchedTeamTest() {
        apiRepository.getSearchedTeams(query)
        Mockito.verify(apiRepository).getSearchedTeams(query)
    }

    @Test
    fun presenterGetSearchedTeamSuccessTest() {
        Mockito.`when`(apiRepository.getSearchedTeams(query)).thenReturn(Calls.response(teamResponse))
        searchPresenter.getTeam(query)
        searchPresenter.onGetSearchedTeamsInteractorSuccess(teamResponse)
        Mockito.verify(searchView).showProgressBar(false)
        Mockito.verify(searchView).showRecyclerView(true)
        Mockito.verify(searchView).setTeamsResult(teamResponse)
    }

    @Test
    fun presenterGetSearchedTeamFailedTest() {
        Mockito.`when`(apiRepository.getSearchedTeams(query)).thenReturn(Calls.response(teamResponse))
        searchPresenter.getTeam(query)
        searchPresenter.onGetSearchedTeamsIntreactorFailed()
        Mockito.verify(searchView).showProgressBar(false)
        Mockito.verify(searchView).showErrorView(true)
    }
}