package com.ericjohnson.footballapps

import com.ericjohnson.footballapps.data.api.MatchDetail
import com.ericjohnson.footballapps.data.api.response.MatchDetailResponse
import com.ericjohnson.footballapps.data.api.setup.ApiRepository
import com.ericjohnson.footballapps.utils.ScheduleType
import com.ericjohnson.footballapps.view.matchescontainer.schedule.SchedulePresenter
import com.ericjohnson.footballapps.view.matchescontainer.schedule.ScheduleView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.mock.Calls
import java.io.IOException


/**
 * Created by johnson on 12/10/18.
 */
class SchedulePresenterTest {

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var scheduleView: ScheduleView

    private lateinit var items: MutableList<MatchDetail>

    private lateinit var response: MatchDetailResponse

    private lateinit var schedulePresenter: SchedulePresenter<ScheduleView>

    private lateinit var leagueId:String

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        schedulePresenter = SchedulePresenter()
        schedulePresenter.onAttach(scheduleView)
        items = mutableListOf()
        response = MatchDetailResponse(items)
        leagueId="4335"
    }

    @Test
    fun apiRepositoryPastScheduleTest() {
        apiRepository.getMatches(ScheduleType.PREVIOUS_EVENT, leagueId)
        verify(apiRepository).getMatches(ScheduleType.PREVIOUS_EVENT, leagueId)
    }

    @Test
    fun presenterPastScheduleSuccessTest() {
        `when`(apiRepository.getMatches(ScheduleType.PREVIOUS_EVENT, leagueId)).thenReturn(Calls.response(response))
        schedulePresenter.getMatches(ScheduleType.PREVIOUS_EVENT,leagueId)
        schedulePresenter.onGetMatchesSuccess(response)
        verify(scheduleView).showSwipeRefresh(false)
        verify(scheduleView).setMatchSchedule(response.events)
    }

    @Test
    fun presenterPastScheduleFailedTest() {
        `when`(apiRepository.getMatches(ScheduleType.PREVIOUS_EVENT, leagueId))
                .thenReturn(Calls.failure(IOException("Error")))
        schedulePresenter.getMatches(ScheduleType.PREVIOUS_EVENT,leagueId)
        schedulePresenter.onGetMatchesFailed()
        verify(scheduleView).showSwipeRefresh(false)
        verify(scheduleView).showError()
    }

    @Test
    fun apiRepositoryNextScheduleTest() {
        apiRepository.getMatches(ScheduleType.NEXT_EVENT,leagueId)
        verify(apiRepository).getMatches(ScheduleType.NEXT_EVENT, leagueId)
    }

    @Test
    fun presenterNextScheduleSuccessTest() {
        `when`(apiRepository.getMatches(ScheduleType.NEXT_EVENT, leagueId)).thenReturn(Calls.response(response))
        schedulePresenter.getMatches(ScheduleType.NEXT_EVENT,leagueId)
        schedulePresenter.onGetMatchesSuccess(response)
        verify(scheduleView).showSwipeRefresh(false)
        verify(scheduleView).setMatchSchedule(response.events)
    }

    @Test
    fun presenterNextScheduleFailedTest() {
        `when`(apiRepository.getMatches(ScheduleType.NEXT_EVENT, leagueId))
                .thenReturn(Calls.failure(IOException("Error")))
        schedulePresenter.getMatches(ScheduleType.NEXT_EVENT,leagueId)
        schedulePresenter.onGetMatchesFailed()
        verify(scheduleView).showSwipeRefresh(false)
        verify(scheduleView).showError()
    }
}