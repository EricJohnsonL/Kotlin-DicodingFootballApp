package com.ericjohnson.footballapps

import com.ericjohnson.footballapps.data.api.League
import com.ericjohnson.footballapps.data.api.TeamDetail
import com.ericjohnson.footballapps.data.api.TeamList
import com.ericjohnson.footballapps.data.api.response.LeagueResponse
import com.ericjohnson.footballapps.data.api.response.TeamDetailResponse
import com.ericjohnson.footballapps.data.api.response.TeamListResponse
import com.ericjohnson.footballapps.data.api.setup.ApiRepository
import com.ericjohnson.footballapps.view.teamdetail.teamoverview.TeamOverviewPresenter
import com.ericjohnson.footballapps.view.teamdetail.teamoverview.TeamOverviewView
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
class TeamOverviewPresenterTest {

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var teamOverviewView: TeamOverviewView

    private lateinit var items: MutableList<TeamDetail>

    private lateinit var response: TeamDetailResponse

    private lateinit var teamId: String

    private lateinit var teamOverviewPresenter: TeamOverviewPresenter<TeamOverviewView>

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        teamOverviewPresenter = TeamOverviewPresenter()
        teamOverviewPresenter.onAttach(teamOverviewView)
        teamId = "133738"
        items = mutableListOf()
        items.add(TeamDetail(teamId, "Real Madrid"))
        response = TeamDetailResponse(items)

    }

    @Test
    fun apiRepositoryGetTeamOverviewTest() {
        apiRepository.getTeamDetail(teamId)
        Mockito.verify(apiRepository).getTeamDetail(teamId)
    }

    @Test
    fun presenterGetTeamOverviewSuccessTest() {
        Mockito.`when`(apiRepository.getTeamDetail(teamId)).thenReturn(Calls.response(response))
        teamOverviewPresenter.getTeamOverview(teamId)
        teamOverviewPresenter.onGetHomeTeamDetailSuccess(response)
        Mockito.verify(teamOverviewView).showProgressBar(false)
        Mockito.verify(teamOverviewView).showTeamOverview(true)
        Mockito.verify(teamOverviewView).setTeamOverview(response.teams[0])
    }

    @Test
    fun presenterGetTeamOverviewFailedTest() {
        Mockito.`when`(apiRepository.getTeamDetail(teamId)).thenReturn(Calls.response(response))
        teamOverviewPresenter.getTeamOverview(teamId)
        teamOverviewPresenter.onGetHomeTeamDetailFailed("Error")
        Mockito.verify(teamOverviewView).showProgressBar(false)
        Mockito.verify(teamOverviewView).showError(true)
    }
}