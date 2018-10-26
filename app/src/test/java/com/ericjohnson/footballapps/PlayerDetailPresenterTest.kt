package com.ericjohnson.footballapps

import com.ericjohnson.footballapps.data.api.PlayerDetail
import com.ericjohnson.footballapps.data.api.response.PlayerDetailResponse
import com.ericjohnson.footballapps.data.api.response.PlayerResponse
import com.ericjohnson.footballapps.data.api.setup.ApiRepository
import com.ericjohnson.footballapps.view.playerdetail.PlayerDetailPresenter
import com.ericjohnson.footballapps.view.playerdetail.PlayerDetailView
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
class PlayerDetailPresenterTest {

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var playerDetailView: PlayerDetailView

    private lateinit var items: MutableList<PlayerDetail>

    private lateinit var response: PlayerDetailResponse

    private lateinit var playerId: String

    private lateinit var playerDetailPresenter: PlayerDetailPresenter<PlayerDetailView>

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        playerDetailPresenter = PlayerDetailPresenter()
        playerDetailPresenter.onAttach(playerDetailView)
        playerId = "34160649 "
        items = mutableListOf()
        items.add(PlayerDetail(playerId,"Marco Asensio"))
        response = PlayerDetailResponse(items)
    }

    @Test
    fun apiRepositoryGetPlayerDetailTest() {
        apiRepository.getPlayerDetail(playerId)
        Mockito.verify(apiRepository).getPlayerDetail(playerId)
    }

    @Test
    fun presenterGetPlayerDetailSuccessTest() {
        Mockito.`when`(apiRepository.getPlayerDetail(playerId)).thenReturn(Calls.response(response))
        playerDetailPresenter.getPlayerDetail(playerId)
        playerDetailPresenter.onGetPlayerDetailInteractorSuccess(response)
        Mockito.verify(playerDetailView).showProgressBar(false)
        Mockito.verify(playerDetailView).showPlayerDetail(true)
        Mockito.verify(playerDetailView).setPlayerDetail(response.player[0])
    }

    @Test
    fun presenterGetPlayerDetailFailedTest() {
        Mockito.`when`(apiRepository.getPlayerDetail(playerId)).thenReturn(Calls.response(response))
        playerDetailPresenter.getPlayerDetail(playerId)
        playerDetailPresenter.onGetPlayerDetailInteractorFailed()
        Mockito.verify(playerDetailView).showProgressBar(false)
        Mockito.verify(playerDetailView).showErrorView(true)
    }
}