package com.ericjohnson.footballapps.view.matchescontainer.schedule


import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.*
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.adapter.recyclerviewadapter.MatchScheduleAdapter
import com.ericjohnson.footballapps.data.api.MatchDetail
import com.ericjohnson.footballapps.event.LeagueChangedEvent
import com.ericjohnson.footballapps.utils.AppConstants
import com.ericjohnson.footballapps.utils.EspressoIdlingResource
import com.ericjohnson.footballapps.utils.TimeUtil
import com.ericjohnson.footballapps.view.matchDetail.MatchDetailActivity
import kotlinx.android.synthetic.main.fragment_schedule.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity
import java.util.*

class ScheduleFragment : Fragment(), ScheduleView, SwipeRefreshLayout.OnRefreshListener {

    private val schedulePresenter: ISchedulePresenter<ScheduleView> = SchedulePresenter()

    private lateinit var scheduleType: String

    private lateinit var adapter: MatchScheduleAdapter

    //default league
    private lateinit var leagueId: String

    companion object {
        @JvmStatic
        fun newInstance(scheduleType: String, leagueId: String) =
                ScheduleFragment().apply {
                    arguments = Bundle().apply {
                        putString(AppConstants.KEY_SCHEDULE_TYPE, scheduleType)
                        putString(AppConstants.KEY_LEAGUE_ID, leagueId)
                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onAttachView()
        EventBus.getDefault().register(this)
        arguments?.let {
            scheduleType = it.getString(AppConstants.KEY_SCHEDULE_TYPE) ?: ""
            leagueId = it.getString(AppConstants.KEY_LEAGUE_ID) ?: ""

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        srl_schedule.setOnRefreshListener(this)
        adapter = MatchScheduleAdapter(ctx, mutableListOf(), scheduleType)
        adapter.clickListener = {
            startActivity<MatchDetailActivity>(AppConstants.KEY_MATCH_DETAIL to it.idEvent)
        }
        adapter.reminderListener = {
            addToCalender(it)
        }

        rv_schedule_list.layoutManager = LinearLayoutManager(ctx)
        rv_schedule_list.hasFixedSize()
        rv_schedule_list.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        onRefresh()
        btn_retry_load_league.setOnClickListener {
            onRefresh()
        }
    }

    private fun addToCalender(matchDetail: MatchDetail) {
        val intent = Intent(Intent.ACTION_INSERT)
        intent.data = CalendarContract.Events.CONTENT_URI
        intent.putExtra(Events.TITLE, "${matchDetail.strHomeTeam} vs ${matchDetail.strAwayTeam}")

        val matchDateTime = TimeUtil.getDateTimeToSplit("${matchDetail.dateEvent} ${matchDetail.strTime}")
        val dateTime = matchDateTime.split(" ")
        val matchDate: GregorianCalendar = GregorianCalendar(dateTime[0].toInt(),
                dateTime[1].toInt() - 1, dateTime[2].toInt(), dateTime[3].toInt(), dateTime[4].toInt(), dateTime[5].toInt())

        intent.putExtra(EXTRA_EVENT_BEGIN_TIME, matchDate.timeInMillis)
        matchDate.add(GregorianCalendar.HOUR, 2)
        intent.putExtra(EXTRA_EVENT_END_TIME, matchDate.timeInMillis)
        startActivity(intent)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        onDetachView()
        super.onDestroy()

    }

    override fun onRefresh() {
        srl_schedule.isRefreshing = true
        ll_empty_schedule.visibility = View.GONE
        rv_schedule_list.visibility = View.GONE
        ev_error_match_list.visibility = View.GONE
        requestMatchData()
    }

    override fun onAttachView() {
        schedulePresenter.onAttach(this)
    }

    override fun onDetachView() {
        schedulePresenter.onDetach()
    }

    private fun requestMatchData() {
        EspressoIdlingResource.increment()
        schedulePresenter.getMatches(scheduleType, leagueId)
    }

    override fun setMatchSchedule(matchSchedule: MutableList<MatchDetail>) {
        rv_schedule_list.visibility = View.VISIBLE
        adapter.addAllItem(matchSchedule)
        EspressoIdlingResource.decrement()
    }

    override fun showError() {
        ev_error_match_list.visibility = View.VISIBLE
        rv_schedule_list.visibility = View.GONE
        EspressoIdlingResource.decrement()
    }

    override fun showSwipeRefresh(isShown: Boolean) {
        when {
            isShown -> onRefresh()
            else -> srl_schedule.isRefreshing = false
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    fun onEvent(leagueChangedEvent: LeagueChangedEvent) {
        leagueId = leagueChangedEvent.leagueId
        onRefresh()
    }

    override fun showEmptyView() {
        ll_empty_schedule.visibility = View.VISIBLE
    }

}
