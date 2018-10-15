package com.ericjohnson.footballapps.view.matchDetail

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.R.drawable.ic_add_to_fav
import com.ericjohnson.footballapps.R.drawable.ic_fav
import com.ericjohnson.footballapps.R.menu.favorites_menu
import com.ericjohnson.footballapps.data.api.MatchDetail
import com.ericjohnson.footballapps.data.db.FavoriteMatch
import com.ericjohnson.footballapps.utils.AppConstants
import com.ericjohnson.footballapps.utils.EspressoIdLingResource
import com.ericjohnson.footballapps.utils.TimeUtil
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.design.snackbar

class MatchDetailActivity : AppCompatActivity(), MatchDetailView {

    private val matchDetailPresenter: IMatchDetailPresenter<MatchDetailView> = MatchDetailPresenter()

    private lateinit var eventId: String

    private var menuItem: Menu? = null

    private var isFavorite: Boolean = false

    private lateinit var favoriteMatch: FavoriteMatch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)
        onAttachView()
        setupToolbar()
        initIntent()
        getMatchDetail(eventId)
        btn_retry_match_detail.setOnClickListener {
            getMatchDetail(eventId)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.title_match_detail)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initIntent() {
        val intent = intent
        eventId = intent.getStringExtra(AppConstants.KEY_MATCH_DETAIL)
    }

    private fun setData(matchDetail: MatchDetail) {
        tv_date.text = TimeUtil.getFormattedDate(matchDetail.dateEvent.toString())
        tv_round.text = (getString(R.string.label_jornada) + " ").plus(matchDetail.intRound)
        tv_home_team.text = matchDetail.strHomeTeam
        tv_away_team.text = matchDetail.strAwayTeam
        tv_home_score.text = when {
            matchDetail.intHomeScore == null -> "-"
            else -> matchDetail.intHomeScore.toString()
        }
        tv_away_score.text = when {
            matchDetail.intAwayScore == null -> "-"
            else -> matchDetail.intAwayScore.toString()
        }

        when {
            matchDetail.intHomeScore == null && matchDetail.intAwayScore == null -> ll_goal_scorer.visibility = View.GONE
            else -> {
                tv_home_goal_scorer.text = matchDetail.strHomeGoalDetails?.replace(";", "\n")?.replace(":", " ")
                tv_away_goal_scorer.text = matchDetail.strAwayGoalDetails?.replace(";", "\n")?.replace(":", " ")
            }
        }

        tv_home_total_shoots.text = when {
            matchDetail.intHomeShots == null -> "-"
            else -> matchDetail.intHomeShots.toString()
        }

        tv_away_total_shoots.text = when {
            matchDetail.intAwayShots == null -> "-"
            else -> matchDetail.intAwayShots.toString()
        }

        tv_home_yellow_card.text = when {
            matchDetail.strHomeYellowCards == null -> "-"
            else -> {
                when {
                    TextUtils.isEmpty(matchDetail.strHomeYellowCards) -> "-"
                    else -> matchDetail.strHomeYellowCards?.replace(";", "\n")?.replace(":", " ")
                }
            }
        }

        tv_away_yellow_card.text = when {
            matchDetail.strAwayYellowCards == null -> "-"
            else -> {
                when {
                    TextUtils.isEmpty(matchDetail.strAwayYellowCards) -> "-"
                    else -> matchDetail.strAwayYellowCards?.replace(";", "\n")?.replace(":", " ")
                }
            }
        }

        tv_home_red_card.text = when {
            matchDetail.strHomeRedCards == null -> "-"
            else -> {
                when {
                    TextUtils.isEmpty(matchDetail.strHomeRedCards) -> "-"
                    else -> matchDetail.strHomeRedCards?.replace(";", "\n")?.replace(":", " ")
                }
            }
        }

        tv_away_red_card.text = when {
            matchDetail.strAwayRedCards == null -> "-"
            else -> {
                when {
                    TextUtils.isEmpty(matchDetail.strAwayRedCards) -> "-"
                    else -> matchDetail.strAwayRedCards?.replace(";", "\n")?.replace(":", " ")
                }
            }
        }

        tv_home_goalkeeper.text = when {
            matchDetail.strHomeLineupGoalkeeper == null -> "-"
            else -> matchDetail.strHomeLineupGoalkeeper?.replace("; ", "")
        }

        tv_away_goalkeeper.text = when {
            matchDetail.strAwayLineupGoalkeeper == null -> "-"
            else -> matchDetail.strAwayLineupGoalkeeper?.replace("; ", "")
        }

        tv_home_defender.text = when {
            matchDetail.strHomeLineupDefense == null -> "-"
            else -> matchDetail.strHomeLineupDefense?.replace("; ", "\n")
        }

        tv_away_defender.text = when {
            matchDetail.strAwayLineupDefense == null -> "-"
            else -> matchDetail.strAwayLineupDefense?.replace("; ", "\n")
        }

        tv_home_midfielder.text = when {
            matchDetail.strHomeLineupMidfield == null -> "-"
            else -> matchDetail.strHomeLineupMidfield?.replace("; ", "\n")
        }

        tv_away_midfielder.text = when {
            matchDetail.strAwayLineupMidfield == null -> "-"
            else -> matchDetail.strAwayLineupMidfield?.replace(";", "\n")
        }

        tv_home_forward.text = when {
            matchDetail.strHomeLineupForward == null -> "-"
            else -> matchDetail.strHomeLineupForward?.replace("; ", "\n")
        }

        tv_away_forward.text = when {
            matchDetail.strAwayLineupForward == null -> "-"
            else -> matchDetail.strAwayLineupForward?.replace("; ", "\n")
        }

        tv_home_subtitutes.text = when {
            matchDetail.strHomeLineupSubstitutes == null -> "-"
            else -> matchDetail.strHomeLineupSubstitutes?.replace("; ", "\n")
        }

        tv_away_subtitutes.text = when {
            matchDetail.strAwayLineupSubstitutes == null -> "-"
            else -> matchDetail.strAwayLineupSubstitutes?.replace("; ", "\n")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
    }


    private fun getMatchDetail(eventId: String) {
        pb_match_detail.visibility = View.VISIBLE
        ev_error_match_detail.visibility = View.GONE
        ll_match_detail.visibility = View.GONE
        EspressoIdLingResource.increment()
        matchDetailPresenter.getMatchDetail(eventId)

    }

    override fun showMatchDetail(matchDetail: MatchDetail) {
        pb_match_detail.visibility = View.GONE
        ev_error_match_detail.visibility = View.GONE
        ll_match_detail.visibility = View.VISIBLE
        setData(matchDetail)
        favoriteMatch = FavoriteMatch(matchDetail.idEvent, matchDetail.dateEvent,
                matchDetail.strHomeTeam, matchDetail.strAwayTeam, matchDetail.intHomeScore,
                matchDetail.intAwayScore)
        matchDetailPresenter.checkFavorites(this, matchDetail.idEvent.toString())
        EspressoIdLingResource.decrement()

        matchDetailPresenter.getHomeTeamBadge(matchDetail.idHomeTeam.toString())
        matchDetailPresenter.getAwayTeamBadge(matchDetail.idAwayTeam.toString())
    }

    override fun showErrorMatchDetail() {
        pb_match_detail.visibility = View.GONE
        ev_error_match_detail.visibility = View.VISIBLE
        EspressoIdLingResource.decrement()
    }


    override fun showHomeTeamBadge(imageUrl: String) {
        Glide.with(this).load(imageUrl).into(img_home_team)
    }

    override fun showAwayTeamBadge(imageUrl: String) {
        Glide.with(this).load(imageUrl).into(img_away_team)
    }

    override fun showImageError(message: String) {
        snackbar(sv_match_detail, message)
    }

    override fun onAttachView() {
        matchDetailPresenter.onAttach(this)
    }

    override fun onDetachView() {
        matchDetailPresenter.onDetach()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(favorites_menu, menu)
        menuItem = menu
        menuItem?.getItem(0)?.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when {
            item?.itemId == android.R.id.home -> onBackPressed()
            item?.itemId == R.id.add_to_favorites -> {
                if (isFavorite) matchDetailPresenter.removeFromFavorites(this, eventId)
                else matchDetailPresenter.setToFavorites(this, favoriteMatch)
                isFavorite != isFavorite
                checkIsFavorite(isFavorite)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun checkIsFavorite(isFavorite: Boolean) {
        this.isFavorite = isFavorite
        menuItem?.getItem(0)?.isVisible = true
        if (this.isFavorite) {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_fav)
        } else {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_fav)
        }

    }

    override fun addTofavoriteSuccess() {
        checkIsFavorite(true)
        snackbar(sv_match_detail, getString(R.string.message_added_to_favorite))
    }

    override fun removeFromFavoriteSuccess() {
        checkIsFavorite(false)
        snackbar(sv_match_detail, getString(R.string.message_remove_to_favorite))
    }

    override fun addOrRemoveFavoriteFailed() {
        snackbar(sv_match_detail, getString(R.string.message_error_favorite))
    }

}
