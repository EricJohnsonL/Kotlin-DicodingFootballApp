package com.ericjohnson.footballapps.view.teamdetail.teamoverview


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.data.api.TeamDetail
import com.ericjohnson.footballapps.utils.AppConstants
import com.ericjohnson.footballapps.utils.EspressoIdlingResource
import kotlinx.android.synthetic.main.fragment_team_overview.*


class TeamOverviewFragment : Fragment(), TeamOverviewView {

    private val presenter: ITeamOverviewPresenter<TeamOverviewView> = TeamOverviewPresenter()

    private lateinit var teamId: String

    companion object {
        @JvmStatic
        fun newInstance(teamId: String) =
                TeamOverviewFragment().apply {
                    arguments = Bundle().apply {
                        putString(AppConstants.KEY_TEAM_DETAIL, teamId)
                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onAttachView()
        arguments?.let {
            teamId = it.getString(AppConstants.KEY_TEAM_DETAIL) ?: ""
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestTeamOverview()
        btn_retry.setOnClickListener {
            requestTeamOverview()
        }
    }

    private fun requestTeamOverview() {
        EspressoIdlingResource.increment()
        presenter.getTeamOverview(teamId)
    }

    override fun onDestroy() {
        onDetachView()
        super.onDestroy()
    }

    override fun showProgressBar(isShown: Boolean) = when {
        isShown -> pb_team_overview.visibility = View.VISIBLE
        else -> pb_team_overview.visibility = View.GONE
    }

    override fun showTeamOverview(isShown: Boolean) = when {
        isShown -> rl_team_overview.visibility = View.VISIBLE
        else -> rl_team_overview.visibility = View.GONE
    }

    override fun setTeamOverview(teamDetail: TeamDetail) {
        tv_year_founded.text = teamDetail.intFormedYear

        tv_year_founded.text = when {
            teamDetail.intFormedYear == null -> "-"
            else -> teamDetail.intFormedYear
        }

        tv_stadium.text = when {
            teamDetail.strStadium == null -> "-"
            else -> teamDetail.strStadium
        }

        tv_coach.text = when {
            teamDetail.strManager == null -> "-"
            else -> teamDetail.strManager
        }

        tv_overview.text = when {
            teamDetail.strDescriptionEn == null -> "-"
            else -> teamDetail.strDescriptionEn
        }

        tv_link.text = when {
            teamDetail.strWebsite == null -> "-"
            else -> teamDetail.strWebsite
        }

        if (teamDetail.strWebsite != null) {
            tv_link.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("http://" + tv_link.text)
                startActivity(intent)
            }
        }
        EspressoIdlingResource.decrement()
    }

    override fun showError(isShown: Boolean) = when {
        isShown -> {
            ev_error_team_overview.visibility = View.VISIBLE
            EspressoIdlingResource.decrement()
        }
        else -> {
            ev_error_team_overview.visibility = View.GONE
        }
    }

    override fun onAttachView() {
        presenter.onAttach(this)
    }

    override fun onDetachView() {
        presenter.onDetach()
    }


}
