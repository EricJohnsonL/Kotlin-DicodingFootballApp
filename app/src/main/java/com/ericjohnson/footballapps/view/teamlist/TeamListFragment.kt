package com.ericjohnson.footballapps.view.teamlist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.adapter.recyclerviewadapter.TeamsAdapter
import com.ericjohnson.footballapps.data.api.League
import com.ericjohnson.footballapps.data.api.TeamList
import com.ericjohnson.footballapps.data.api.response.LeagueResponse
import com.ericjohnson.footballapps.utils.AppConstants
import com.ericjohnson.footballapps.utils.EspressoIdlingResource
import com.ericjohnson.footballapps.view.teamdetail.TeamDetailActivity
import kotlinx.android.synthetic.main.fragment_team_list.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity

class TeamListFragment : Fragment(), TeamListView, AdapterView.OnItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    private val teamListPresenter: ITeamListPresenter<TeamListView> = TeamListPresenter()

    private lateinit var leagueManualAdapter: ArrayAdapter<League>

    private lateinit var teamsAdapter: TeamsAdapter

    private lateinit var leagueId: String

    override fun onAttachView() {
        teamListPresenter.onAttach(this)
    }

    override fun onDetachView() {
        teamListPresenter.onDetach()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onAttachView()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EspressoIdlingResource.increment()
        teamListPresenter.getLeague()
    }


    override fun onRefresh() {
        srl_teams.isRefreshing = true
        rv_teams.visibility = View.GONE
        ev_error_team_list.visibility = View.GONE
        showEmptyTeam(false)
        requestTeamList(leagueId)
    }

    private fun requestTeamList(leagueId: String) {
        EspressoIdlingResource.increment()
        teamListPresenter.getTeamList(leagueId)
    }

    override fun setTeamList(teamList: MutableList<TeamList>) {
        rv_teams.visibility = View.VISIBLE
        teamsAdapter.addAllItem(teamList)
        EspressoIdlingResource.decrement()
    }

    override fun showError() {
        ev_error_team_list.visibility = View.VISIBLE
        rv_teams.visibility = View.GONE
        EspressoIdlingResource.decrement()
    }

    override fun showSwipeRefresh(isShown: Boolean) {
        when {
            isShown -> onRefresh()
            else -> srl_teams.isRefreshing = false
        }
    }

    override fun onDestroy() {
        onDetachView()
        super.onDestroy()
    }

    override fun setLeague(leagueResponse: LeagueResponse) {
        leagueManualAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, leagueResponse.league)
        leagueManualAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spn_league.adapter = leagueManualAdapter
        spn_league.onItemSelectedListener = this
        EspressoIdlingResource.decrement()

        leagueId = (spn_league.getItemAtPosition(0) as League).idLeague

        srl_teams.setOnRefreshListener(this)

        teamsAdapter = TeamsAdapter(ctx, mutableListOf())
        teamsAdapter.clickListener = {
            startActivity<TeamDetailActivity>(AppConstants.KEY_TEAM_DETAIL to it)
        }
        rv_teams.layoutManager = LinearLayoutManager(ctx)
        rv_teams.hasFixedSize()
        rv_teams.adapter = teamsAdapter

        onRefresh()
        btn_retry.setOnClickListener {
            onRefresh()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val league: League = parent?.getItemAtPosition(position) as League
        leagueId = league.idLeague
        onRefresh()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) = Unit

    override fun showTeamList(isShown: Boolean) = when {
        isShown -> ll_team_list.visibility = View.VISIBLE
        else -> ll_team_list.visibility = View.GONE
    }

    override fun showProgressBar(isShown: Boolean) = when {
        isShown -> pb_team_list.visibility = View.VISIBLE
        else -> pb_team_list.visibility = View.GONE
    }

    override fun showErrorLeague(isShown: Boolean) = when {
        isShown -> ev_error_team_list.visibility = View.VISIBLE
        else -> ev_error_team_list.visibility = View.GONE
    }

    override fun showEmptyTeam(isShown: Boolean) = when {
        isShown -> ll_empty_league.visibility = View.VISIBLE
        else -> ll_empty_league.visibility = View.GONE
    }


}
