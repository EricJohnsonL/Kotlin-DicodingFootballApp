package com.ericjohnson.footballapps.view.matchescontainer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.adapter.pageradapter.MatchesPagerAdapter
import com.ericjohnson.footballapps.data.api.League
import com.ericjohnson.footballapps.data.api.response.LeagueResponse
import com.ericjohnson.footballapps.event.LeagueChangedEvent
import com.ericjohnson.footballapps.utils.LeagueUtil
import com.ericjohnson.footballapps.utils.ScheduleType
import com.ericjohnson.footballapps.view.matchescontainer.schedule.ScheduleFragment
import kotlinx.android.synthetic.main.fragment_matches_container.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.support.v4.ctx

class MatchesContainer : Fragment(), MatchesContainerView, AdapterView.OnItemSelectedListener {

    private val matchesContainerPresenter: IMatchesContainerPresenter<MatchesContainerView> = MatchesContainerPresenter()

    private var titles: MutableList<String> = mutableListOf()

    private var fragment: MutableList<ScheduleFragment> = mutableListOf()

    private lateinit var matchesPagerAdapter: MatchesPagerAdapter

    private lateinit var leagueManualAdapter: ArrayAdapter<League>

    private lateinit var leagueId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onAttachView()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_matches_container, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        matchesContainerPresenter.getLeague()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val league: League = parent?.getItemAtPosition(position) as League
        leagueId = league.idLeague
        EventBus.getDefault().post(LeagueChangedEvent(leagueId))
    }

    override fun onNothingSelected(parent: AdapterView<*>?) = Unit

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
    }

    private fun setupViewPager() {
        titles.add(getString(R.string.title_last_match))
        titles.add(getString(R.string.title_next_match))

        fragment.add(ScheduleFragment.newInstance(ScheduleType.PREVIOUS_EVENT, leagueId))
        fragment.add(ScheduleFragment.newInstance(ScheduleType.NEXT_EVENT, leagueId))

        matchesPagerAdapter = MatchesPagerAdapter(fragmentManager, fragment, titles)
        vp_match_schedule.adapter = matchesPagerAdapter
        vp_match_schedule.offscreenPageLimit = 0
        tab_match_schedule.setupWithViewPager(vp_match_schedule)
    }

    override fun onAttachView() {
        matchesContainerPresenter.onAttach(this)
    }

    override fun onDetachView() {
        matchesContainerPresenter.onDetach()
    }

    override fun showProgressBar(isShown: Boolean) = when {
        isShown -> pb_matches_container.visibility = View.VISIBLE
        else -> pb_matches_container.visibility = View.GONE
    }

    override fun showLayout(isShown: Boolean) = when {
        isShown -> ll_matches_container.visibility = View.VISIBLE
        else -> ll_matches_container.visibility = View.GONE
    }

    override fun setLeague(leagueResponse: LeagueResponse) {
        leagueManualAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, leagueResponse.league)
        leagueManualAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spn_league.adapter = leagueManualAdapter

        val league: League = spn_league.getItemAtPosition(0) as League
        leagueId = league.idLeague
        EventBus.getDefault().post(LeagueChangedEvent(leagueId))

        spn_league.onItemSelectedListener = this
        setupViewPager()
    }

    override fun showError(isShown: Boolean) = when {
        isShown -> ev_error_matches_container.visibility = View.VISIBLE
        else -> ev_error_matches_container.visibility = View.GONE
    }

}
