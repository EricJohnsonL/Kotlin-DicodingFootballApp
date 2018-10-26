package com.ericjohnson.footballapps

import com.ericjohnson.footballapps.data.api.PlayerDetail
import com.ericjohnson.footballapps.data.api.response.PlayerResponse
import com.ericjohnson.footballapps.data.api.setup.ApiRepository
import com.ericjohnson.footballapps.view.teamdetail.teamplayer.TeamPlayerPresenter
import com.ericjohnson.footballapps.view.teamdetail.teamplayer.TeamPlayerView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.mock.Calls

/**
 * Created by johnson on 10/25/18.
 */
class TeamPlayerPresenterTest {

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var teamPlayerView: TeamPlayerView

    private lateinit var items: MutableList<PlayerDetail>

    private lateinit var response: PlayerResponse

    private lateinit var teamId: String

    private lateinit var teamPlayerPresenter: TeamPlayerPresenter<TeamPlayerView>

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        teamPlayerPresenter = TeamPlayerPresenter()
        teamPlayerPresenter.onAttach(teamPlayerView)
        teamId = "133738"
        items = mutableListOf()
        response = PlayerResponse(items)

    }

    @Test
    fun apiRepositoryGetTeamPlayerTest() {
        apiRepository.getPlayerByTeam(teamId)
        Mockito.verify(apiRepository).getPlayerByTeam(teamId)
    }

    @Test
    fun presenterGetTeamPlayerSuccessTest() {
        Mockito.`when`(apiRepository.getPlayerByTeam(teamId)).thenReturn(Calls.response(response))
        teamPlayerPresenter.getPlayers(teamId)
        teamPlayerPresenter.onGetTeamPlayersInteractorSuccess(response)
        Mockito.verify(teamPlayerView).showProgressBar(false)
        Mockito.verify(teamPlayerView).showPlayers(true)
        Mockito.verify(teamPlayerView).setPlayers(response)
    }

    @Test
    fun presenterGetTeamPlayerFailedTest() {
        Mockito.`when`(apiRepository.getPlayerByTeam(teamId)).thenReturn(Calls.response(response))
        teamPlayerPresenter.getPlayers(teamId)
        teamPlayerPresenter.onGetTeamPlayersIntreactorFailed()
        Mockito.verify(teamPlayerView).showProgressBar(false)
        Mockito.verify(teamPlayerView).showError(true)
    }
}