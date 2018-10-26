package com.ericjohnson.footballapps

import com.ericjohnson.footballapps.data.api.League
import com.ericjohnson.footballapps.data.api.response.LeagueResponse
import com.ericjohnson.footballapps.data.api.setup.ApiRepository
import com.ericjohnson.footballapps.view.matchescontainer.MatchesContainerPresenter
import com.ericjohnson.footballapps.view.matchescontainer.MatchesContainerView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.mock.Calls

/**
 * Created by johnson on 10/25/18.
 */
class MatchesContainerPresenterTest {

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var matchesContainerView: MatchesContainerView

    private lateinit var items: MutableList<League>

    private lateinit var response: LeagueResponse

    private lateinit var matchesContainerPresenter: MatchesContainerPresenter<MatchesContainerView>

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        matchesContainerPresenter = MatchesContainerPresenter()
        matchesContainerPresenter.onAttach(matchesContainerView)
        items = mutableListOf()
        response = LeagueResponse(items)
    }

    @Test
    fun apiRepositoryGetLeagueTest() {
        apiRepository.getLeague()
        Mockito.verify(apiRepository).getLeague()
    }

    @Test
    fun presenterGetLeagueSuccessTest() {
        Mockito.`when`(apiRepository.getLeague()).thenReturn(Calls.response(response))
        matchesContainerPresenter.getLeague()
        matchesContainerPresenter.onGetLeagueInteractorSuccess(response)
        Mockito.verify(matchesContainerView).showProgressBar(false)
        Mockito.verify(matchesContainerView).showLayout(true)
        Mockito.verify(matchesContainerView).setLeague(response)
    }

    @Test
    fun presenterGetLeagueFailedTest() {
        Mockito.`when`(apiRepository.getLeague()).thenReturn(Calls.response(response))
        matchesContainerPresenter.getLeague()
        matchesContainerPresenter.onGetLeagueInteractorFailed()
        Mockito.verify(matchesContainerView).showProgressBar(false)
        Mockito.verify(matchesContainerView).showError(true)
    }
}