package com.ericjohnson.footballapps.view.schedule


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.adapter.MatchScheduleAdapter
import com.ericjohnson.footballapps.data.api.MatchDetail
import com.ericjohnson.footballapps.utils.AppConstants
import com.ericjohnson.footballapps.view.matchDetail.MatchDetailActivity
import kotlinx.android.synthetic.main.fragment_schedule.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity

class ScheduleFragment : Fragment(), ScheduleView, SwipeRefreshLayout.OnRefreshListener {

    private val schedulePresenter: ISchedulePresenter<ScheduleView> = SchedulePresenter()

    private var scheduleType: String? = null

    private lateinit var adapter: MatchScheduleAdapter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        onAttachView()
    }

    override fun onDetach() {
        super.onDetach()
        onDetachView()
    }

    companion object {
        @JvmStatic
        fun newInstance(scheduleType: String) =
                ScheduleFragment().apply {
                    arguments = Bundle().apply {
                        putString(AppConstants.KEY_SCHEDULE_TYPE, scheduleType)
                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            scheduleType = it.getString(AppConstants.KEY_SCHEDULE_TYPE)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        srl_schedule.setOnRefreshListener(this)
        adapter = MatchScheduleAdapter(ctx, mutableListOf()) {
            startActivity<MatchDetailActivity>(AppConstants.KEY_MATCH_DETAIL to it)
        }
        rv_schedule_list.layoutManager = LinearLayoutManager(ctx)
        rv_schedule_list.hasFixedSize()
        rv_schedule_list.adapter = adapter

        onRefresh()
        btn_retry_load_league.setOnClickListener {
            onRefresh()
        }
    }

    override fun onRefresh() {
        srl_schedule.isRefreshing = true
        rv_schedule_list.visibility = View.GONE
        ev_error_league_list.visibility = View.GONE
        requestMatchData()
    }

    override fun onAttachView() {
        schedulePresenter.onAttach(this)
    }

    override fun onDetachView() {
        schedulePresenter.onDetach()
    }

    override fun setMatchSchedule(matchSchedule: MutableList<MatchDetail>) {
        rv_schedule_list.visibility = View.VISIBLE
        adapter.addAllItem(matchSchedule)
    }

    override fun showError() {
        ev_error_league_list.visibility = View.VISIBLE
        rv_schedule_list.visibility = View.GONE
    }

    override fun showSwipeRefresh(isShown: Boolean) {
        when {
            isShown -> onRefresh()
            else -> srl_schedule.isRefreshing = false
        }
    }

    private fun requestMatchData() {
        schedulePresenter.getMatches(scheduleType.toString())
    }


}
