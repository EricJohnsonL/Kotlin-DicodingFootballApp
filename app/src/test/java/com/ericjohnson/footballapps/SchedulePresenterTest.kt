package com.ericjohnson.footballapps

import com.ericjohnson.footballapps.data.api.MatchDetail
import com.ericjohnson.footballapps.data.api.response.MatchDetailResponse
import com.ericjohnson.footballapps.data.api.setup.ApiRepository
import com.ericjohnson.footballapps.utils.ScheduleType
import com.ericjohnson.footballapps.view.schedule.SchedulePresenter
import com.ericjohnson.footballapps.view.schedule.ScheduleView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
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

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        schedulePresenter = SchedulePresenter()
        schedulePresenter.onAttach(scheduleView)
        items = mutableListOf()
        response = MatchDetailResponse(items)
    }

    @Test
    fun apiRepositoryPastScheduleTest() {
        apiRepository.getMatches(ScheduleType.PREVIOUS_EVENT, BuildConfig.LEAGUE_ID)
        verify(apiRepository).getMatches(ScheduleType.PREVIOUS_EVENT, BuildConfig.LEAGUE_ID)
    }

    @Test
    fun presenterPastScheduleSuccessTest() {
        `when`(apiRepository.getMatches(ScheduleType.PREVIOUS_EVENT, BuildConfig.LEAGUE_ID)).thenReturn(Calls.response(response))
        schedulePresenter.getMatches(ScheduleType.PREVIOUS_EVENT)
        schedulePresenter.onGetMatchesSuccess(response)
        verify(scheduleView).showSwipeRefresh(false)
        verify(scheduleView).setMatchSchedule(response.events)
    }

    @Test
    fun presenterPastScheduleFailedTest() {
        `when`(apiRepository.getMatches(ScheduleType.PREVIOUS_EVENT, BuildConfig.LEAGUE_ID))
                .thenReturn(Calls.failure(IOException("Error")))
        schedulePresenter.getMatches(ScheduleType.PREVIOUS_EVENT)
        schedulePresenter.onGetMatchesFailed()
        verify(scheduleView).showSwipeRefresh(false)
        verify(scheduleView).showError()
    }

    @Test
    fun apiRepositoryNextScheduleTest() {
        apiRepository.getMatches(ScheduleType.NEXT_EVENT, BuildConfig.LEAGUE_ID)
        verify(apiRepository).getMatches(ScheduleType.NEXT_EVENT, BuildConfig.LEAGUE_ID)
    }

    @Test
    fun presenterNextScheduleSuccessTest() {
        `when`(apiRepository.getMatches(ScheduleType.NEXT_EVENT, BuildConfig.LEAGUE_ID)).thenReturn(Calls.response(response))
        schedulePresenter.getMatches(ScheduleType.NEXT_EVENT)
        schedulePresenter.onGetMatchesSuccess(response)
        verify(scheduleView).showSwipeRefresh(false)
        verify(scheduleView).setMatchSchedule(response.events)
    }

    @Test
    fun presenterNextScheduleFailedTest() {
        `when`(apiRepository.getMatches(ScheduleType.NEXT_EVENT, BuildConfig.LEAGUE_ID))
                .thenReturn(Calls.failure(IOException("Error")))
        schedulePresenter.getMatches(ScheduleType.NEXT_EVENT)
        schedulePresenter.onGetMatchesFailed()
        verify(scheduleView).showSwipeRefresh(false)
        verify(scheduleView).showError()
    }
}