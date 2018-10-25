package com.ericjohnson.footballapps.view.teamdetail.teamplayer


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.adapter.recyclerviewadapter.TeamPlayerAdapter
import com.ericjohnson.footballapps.data.api.PlayerDetail
import com.ericjohnson.footballapps.data.api.TeamDetail
import com.ericjohnson.footballapps.data.api.response.PlayerResponse
import com.ericjohnson.footballapps.utils.AppConstants
import com.ericjohnson.footballapps.view.playerdetail.PlayerDetailActivity
import com.ericjohnson.footballapps.view.teamdetail.TeamDetailActivity
import kotlinx.android.synthetic.main.fragment_team_player.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity

class TeamPlayerFragment : Fragment(), TeamPlayerView {

    private val presenter: ITeamPlayerPresenter<TeamPlayerView> = TeamPlayerPresenter()

    private lateinit var teamId: String

    private lateinit var teamPlayerAdapter: TeamPlayerAdapter

    companion object {
        @JvmStatic
        fun newInstance(teamId: String) =
                TeamPlayerFragment().apply {
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
        return inflater.inflate(R.layout.fragment_team_player, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        teamPlayerAdapter = TeamPlayerAdapter(ctx, mutableListOf())
        teamPlayerAdapter.clickListener = {
            startActivity<PlayerDetailActivity>(AppConstants.KEY_PLAYER_ID to it.idPlayer,
                    AppConstants.KEY_PLAYER_NAME to it.strPlayer)
        }
        rv_players.layoutManager = LinearLayoutManager(ctx)
        rv_players.hasFixedSize()
        rv_players.adapter = teamPlayerAdapter

        requestTeamPlayer()
        btn_retry.setOnClickListener {
            requestTeamPlayer()
        }
    }

    private fun requestTeamPlayer() {
        presenter.getPlayers(teamId)
    }


    override fun onDestroy() {
        onDetachView()
        super.onDestroy()
    }


    override fun showProgressBar(isShown: Boolean) = when {
        isShown -> pb_team_player.visibility = View.VISIBLE
        else -> pb_team_player.visibility = View.GONE
    }

    override fun showPlayers(isShown: Boolean) = when {
        isShown -> rv_players.visibility = View.VISIBLE
        else -> rv_players.visibility = View.GONE
    }

    override fun setPlayers(playerResponse: PlayerResponse) {
        if (playerResponse.player == null) {
            teamPlayerAdapter.clearItem()
        } else {
            teamPlayerAdapter.addAllItem(playerResponse.player)
        }
    }

    override fun showError(isShown: Boolean) = when {
        isShown -> ev_error_team_player.visibility = View.VISIBLE
        else -> ev_error_team_player.visibility = View.GONE
    }

    override fun showEmptyData(isShown: Boolean)  = when {
        isShown -> ll_empty_player.visibility = View.VISIBLE
        else -> ll_empty_player.visibility = View.GONE
    }


    override fun onAttachView() {
        presenter.onAttach(this)
    }

    override fun onDetachView() {
        presenter.onDetach()
    }
}
