package com.ericjohnson.footballapps

import com.ericjohnson.footballapps.data.api.League
import com.ericjohnson.footballapps.data.api.TeamList
import com.ericjohnson.footballapps.data.api.response.LeagueResponse
import com.ericjohnson.footballapps.data.api.response.TeamListResponse
import com.ericjohnson.footballapps.data.api.setup.ApiRepository
import com.ericjohnson.footballapps.view.matchescontainer.MatchesContainerPresenter
import com.ericjohnson.footballapps.view.matchescontainer.MatchesContainerView
import com.ericjohnson.footballapps.view.teamlist.TeamListPresenter
import com.ericjohnson.footballapps.view.teamlist.TeamListView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.mock.Calls

/**
 * Created by johnson on 10/25/18.
 */
class TeamListPresenterTest {

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var teamListView: TeamListView

    private lateinit var leagueItems: MutableList<League>

    private lateinit var leagueResponse: LeagueResponse

    private lateinit var teamListItems: MutableList<TeamList>

    private lateinit var teamListResponse: TeamListResponse

    private lateinit var teamListPresenter: TeamListPresenter<TeamListView>

    private lateinit var leagueId:String

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        teamListPresenter = TeamListPresenter()
        teamListPresenter.onAttach(teamListView)
        teamListItems = mutableListOf()
        teamListResponse = TeamListResponse(teamListItems)
        leagueItems = mutableListOf()
        leagueResponse = LeagueResponse(leagueItems)
        leagueId="4335"
    }

    @Test
    fun apiRepositoryGetLeagueTest() {
        apiRepository.getLeague()
        Mockito.verify(apiRepository).getLeague()
    }

    @Test
    fun presenterGetLeagueSuccessTest() {
        Mockito.`when`(apiRepository.getLeague()).thenReturn(Calls.response(leagueResponse))
        teamListPresenter.getLeague()
        teamListPresenter.onGetLeagueInteractorSuccess(leagueResponse)
        Mockito.verify(teamListView).showProgressBar(false)
        Mockito.verify(teamListView).showTeamList(true)
        Mockito.verify(teamListView).setLeague(leagueResponse)
    }

    @Test
    fun presenterGetLeagueFailedTest() {
        Mockito.`when`(apiRepository.getLeague()).thenReturn(Calls.response(leagueResponse))
        teamListPresenter.getLeague()
        teamListPresenter.onGetLeagueInteractorFailed()
        Mockito.verify(teamListView).showProgressBar(false)
        Mockito.verify(teamListView).showErrorLeague(true)
    }

    @Test
    fun apiRepositoryGetTeamListTest() {
        apiRepository.getTeamList(leagueId)
        Mockito.verify(apiRepository).getTeamList(leagueId)
    }

    @Test
    fun presenterGetTeamListSuccessTest() {
        Mockito.`when`(apiRepository.getTeamList(leagueId)).thenReturn(Calls.response(teamListResponse))
        teamListPresenter.getTeamList(leagueId)
        teamListPresenter.onGetTeamListInteractorSuccess(teamListResponse)
        Mockito.verify(teamListView).showSwipeRefresh(false)
        Mockito.verify(teamListView).setTeamList(teamListResponse.teams)
    }

    @Test
    fun presenterGetTeamListFailedTest() {
        Mockito.`when`(apiRepository.getTeamList(leagueId)).thenReturn(Calls.response(teamListResponse))
        teamListPresenter.getTeamList(leagueId)
        teamListPresenter.onGetTeamListIntreactorFailed()
        Mockito.verify(teamListView).showSwipeRefresh(false)
        Mockito.verify(teamListView).showError()
    }
}