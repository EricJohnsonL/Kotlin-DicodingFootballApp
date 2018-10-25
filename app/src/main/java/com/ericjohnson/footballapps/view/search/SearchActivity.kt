package com.ericjohnson.footballapps.view.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.R.id.*
import com.ericjohnson.footballapps.adapter.recyclerviewadapter.MatchScheduleAdapter
import com.ericjohnson.footballapps.adapter.recyclerviewadapter.TeamsAdapter
import com.ericjohnson.footballapps.data.api.response.SearchedMatchResponse
import com.ericjohnson.footballapps.data.api.response.TeamListResponse
import com.ericjohnson.footballapps.utils.AppConstants
import com.ericjohnson.footballapps.utils.ScheduleType
import com.ericjohnson.footballapps.utils.SearchType
import com.ericjohnson.footballapps.view.matchDetail.MatchDetailActivity
import com.ericjohnson.footballapps.view.teamdetail.TeamDetailActivity
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SearchActivity : AppCompatActivity(), SearchView {

    private val presenter: ISearchPresenter<SearchView> = SearchPresenter()

    private lateinit var scheduleAdapter: MatchScheduleAdapter

    private lateinit var teamListAdapter: TeamsAdapter

    private lateinit var searchType: String

    private lateinit var query: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        onAttachView()
        initIntent()
        setupToolbar()
        initView()
        initAction()
    }

    private fun initIntent() {
        searchType = intent.getStringExtra(AppConstants.KEY_SEARCH_TYPE)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val title: String = when (searchType) {
            SearchType.teams -> getString(R.string.title_search_teams)
            else -> getString(R.string.title_search_match)
        }

        supportActionBar?.title = title
    }

    private fun initView() {
        teamListAdapter = TeamsAdapter(this, mutableListOf())
        teamListAdapter.clickListener = {
            startActivity<TeamDetailActivity>(AppConstants.KEY_TEAM_DETAIL to it)
        }

        scheduleAdapter = MatchScheduleAdapter(this, mutableListOf(), ScheduleType.PREVIOUS_EVENT)
        scheduleAdapter.clickListener = {
            startActivity<MatchDetailActivity>(AppConstants.KEY_MATCH_DETAIL to it.idEvent)
        }
        scheduleAdapter.reminderListener = {

        }

        rv_item.layoutManager = LinearLayoutManager(this)
        rv_item.hasFixedSize()

        when (searchType) {
            SearchType.teams -> rv_item.adapter = teamListAdapter
            else -> rv_item.adapter = scheduleAdapter
        }
        search_view.requestFocus()
        search_view.isFocusable = true
    }

    private fun initAction() {
        search_view.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                query = if (newText.equals("")) {
                    "\"\""
                } else {
                    newText.toString()
                }

                when (searchType) {
                    SearchType.teams -> presenter.getTeam(query)
                    else -> presenter.getMathes(query)
                }

                return true
            }
        })

        btn_retry.setOnClickListener {
            when (searchType) {
                SearchType.teams -> presenter.getTeam(query)
                else -> presenter.getMathes(query)
            }
        }
    }

    override fun onDestroy() {
        onDetachView()
        super.onDestroy()
    }

    override fun showProgressBar(isShown: Boolean) = when {
        isShown -> pb_search.visibility = View.VISIBLE
        else -> pb_search.visibility = View.GONE
    }

    override fun showRecyclerView(isShown: Boolean) = when {
        isShown -> rv_item.visibility = View.VISIBLE
        else -> rv_item.visibility = View.GONE
    }

    override fun showErrorView(isShown: Boolean) = when {
        isShown -> ev_error_search.visibility = View.VISIBLE
        else -> ev_error_search.visibility = View.GONE
    }

    override fun setMatchesResult(searchedMatchResponse: SearchedMatchResponse) {
        if (searchedMatchResponse.event == null) {
            scheduleAdapter.clearItem()
        } else {
            scheduleAdapter.addAllItem(searchedMatchResponse.event)
        }
    }

    override fun setTeamsResult(teamListResponse: TeamListResponse) {

        if (teamListResponse.teams == null) {
            teamListAdapter.clearItem()
        } else {
            teamListAdapter.addAllItem(teamListResponse.teams)
        }
    }

    override fun onAttachView() {
        presenter.onAttach(this)
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when {
            item?.itemId == android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
