package com.ericjohnson.footballapps

import com.ericjohnson.footballapps.data.api.MatchDetail
import com.ericjohnson.footballapps.data.api.TeamDetail
import com.ericjohnson.footballapps.data.api.response.MatchDetailResponse
import com.ericjohnson.footballapps.data.api.response.TeamDetailResponse
import com.ericjohnson.footballapps.data.api.setup.ApiRepository
import com.ericjohnson.footballapps.view.matchDetail.MatchDetailPresenter
import com.ericjohnson.footballapps.view.matchDetail.MatchDetailView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.mock.Calls
import java.io.IOException

/**
 * Created by johnson on 10/14/18.
 */
class MatchDetailPresenterTest {

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var matchDetailView: MatchDetailView

    private lateinit var items: MutableList<MatchDetail>

    private lateinit var response: MatchDetailResponse

    private lateinit var item: MatchDetail

    private lateinit var teamDetailResponse: TeamDetailResponse

    private lateinit var teamDetails: MutableList<TeamDetail>

    private lateinit var matchDetailPresenter: MatchDetailPresenter<MatchDetailView>

    private val matchId: String = "584426"

    private val teamId: String = "133738"

    private val strTeamBadge: String = "https://www.thesportsdb.com/images/media/team/badge/vwvwrw1473502969.png"

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        matchDetailPresenter = MatchDetailPresenter()
        matchDetailPresenter.onAttach(matchDetailView)
        items = mutableListOf()
        item = MatchDetail("584436", "Real Madrid", "Levante", null, null,
                null, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null, null, 9,null)
        items.add(item)
        response = MatchDetailResponse(items)

        teamDetails = mutableListOf()
        teamDetails.add(TeamDetail(strTeamBadge))
        teamDetailResponse = TeamDetailResponse(teamDetails)
    }

    @Test
    fun apiRepositoryMatchDetailTest() {
        apiRepository.getMatchDetail(matchId)
        Mockito.verify(apiRepository).getMatchDetail(matchId)
    }

    @Test
    fun presenterMatchDetailSuccessTest() {
        Mockito.`when`(apiRepository.getMatchDetail(matchId)).thenReturn(Calls.response(response))
        matchDetailPresenter.getMatchDetail(matchId)
        matchDetailPresenter.onGetMatchDetailSuccess(response.events[0])
        Mockito.verify(matchDetailView).showMatchDetail(response.events[0])
    }

    @Test
    fun presenterMatchDetailFailedTest() {
        Mockito.`when`(apiRepository.getMatchDetail(matchId)).thenReturn(Calls.failure(IOException("Error")))
        matchDetailPresenter.getMatchDetail(matchId)
        matchDetailPresenter.onGetMatchDetailFailed()
        Mockito.verify(matchDetailView).showErrorMatchDetail()
    }

    @Test
    fun presenterGetBadgeTest() {
        apiRepository.getTeamDetail(teamId)
        Mockito.verify(apiRepository).getTeamDetail(teamId)
    }

    @Test
    fun presenterGetBadgeSuccessTest() {
        Mockito.`when`(apiRepository.getTeamDetail(teamId)).thenReturn(Calls.response(teamDetailResponse))
        matchDetailPresenter.getHomeTeamBadge(teamId)
        matchDetailPresenter.onGetHomeTeamDetailSuccess(teamDetailResponse)
        teamDetailResponse.teams[0].strTeamBadge?.let { Mockito.verify(matchDetailView).showHomeTeamBadge(it) }
    }

    @Test
    fun presenterGetBadgeFailedTest() {
        Mockito.`when`(apiRepository.getTeamDetail("")).thenReturn(Calls.failure(IOException("Error")))
        matchDetailPresenter.getHomeTeamBadge("")
        matchDetailPresenter.onGetHomeTeamDetailFailed("Error")
        Mockito.verify(matchDetailView).showImageError("Error")
    }

}