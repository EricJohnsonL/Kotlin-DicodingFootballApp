package com.ericjohnson.footballapps.view.teamdetail

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.adapter.pageradapter.TeamDetailPagerAdapter
import com.ericjohnson.footballapps.data.api.TeamList
import com.ericjohnson.footballapps.data.db.FavoriteTeam
import com.ericjohnson.footballapps.utils.AppConstants
import com.ericjohnson.footballapps.view.teamdetail.teamoverview.TeamOverviewFragment
import com.ericjohnson.footballapps.view.teamdetail.teamplayer.TeamPlayerFragment
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.jetbrains.anko.design.snackbar


class TeamDetailActivity : AppCompatActivity(), TeamDetailView {

    private val teamDetailPresenter: ITeamDetailPresenter<TeamDetailView> = TeamDetailPresenter()

    private lateinit var teamList: TeamList

    private var titles: MutableList<String> = mutableListOf()

    private var fragment: MutableList<Fragment> = mutableListOf()

    private lateinit var teamDetailPagerAdapter: TeamDetailPagerAdapter

    private var menuItem: Menu? = null

    private var isFavorite: Boolean = false

    private lateinit var teamId: String

    private lateinit var favoriteTeam: FavoriteTeam

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)
        onAttachView()
        initIntent()
        setToolbar()
        initCollapsingToolbar()
        initView()
        setupViewPager()
    }

    private fun initIntent() {
        teamList = intent.getParcelableExtra(AppConstants.KEY_TEAM_DETAIL)
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initView() {
        teamId = teamList.idTeam
        favoriteTeam = FavoriteTeam(teamList.idTeam, teamList.strTeam, teamList.strTeamBadge.toString())
        val requestOptions = RequestOptions()
                .error(R.drawable.ic_image)
                .fallback(R.drawable.ic_image)
        Glide.with(this).setDefaultRequestOptions(requestOptions)
                .load(teamList.strTeamBadge).into(img_club)
        tv_club_name.text = teamList.strTeam

    }

    private fun initCollapsingToolbar() {
        collapsing_toolbar.title = ""
        appbar.setExpanded(true)

        appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = false

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (appBarLayout.totalScrollRange + verticalOffset <= appBarLayout.totalScrollRange * 0.35) {
                    collapsing_toolbar.title = teamList.strTeam
                    isShow = true
                } else if (isShow) {
                    collapsing_toolbar.title = ""
                    isShow = false
                }
            }
        })
    }

    private fun setupViewPager() {
        titles.add(getString(R.string.label_overview))
        titles.add(getString(R.string.label_players))

        fragment.add(TeamOverviewFragment.newInstance(teamList.idTeam))
        fragment.add(TeamPlayerFragment.newInstance(teamList.idTeam))

        teamDetailPagerAdapter = TeamDetailPagerAdapter(supportFragmentManager, fragment, titles)
        vp_team_detail.adapter = teamDetailPagerAdapter
        vp_team_detail.offscreenPageLimit = 0
        tablayout_team.setupWithViewPager(vp_team_detail)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.favorites_menu, menu)
        menuItem = menu
        teamDetailPresenter.checkFavorites(this, teamId)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when {
            item?.itemId == android.R.id.home -> onBackPressed()
            item?.itemId == R.id.add_to_favorites -> {
                if (isFavorite) teamDetailPresenter.removeFromFavorites(this, teamId)
                else teamDetailPresenter.setToFavorites(this, favoriteTeam)
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
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_fav)
        } else {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_fav)
        }
    }

    override fun onDestroy() {
        onDetachView()
        super.onDestroy()
    }

    override fun onAttachView() {
        teamDetailPresenter.onAttach(this)
    }

    override fun onDetachView() {
        teamDetailPresenter.onDetach()
    }

    override fun addTofavoriteSuccess() {
        checkIsFavorite(true)
        snackbar(cl_team_detail, getString(R.string.message_added_to_favorite))
    }

    override fun removeFromFavoriteSuccess() {
        checkIsFavorite(false)
        snackbar(cl_team_detail, getString(R.string.message_remove_to_favorite))
    }

    override fun addOrRemoveFavoriteFailed() {
        snackbar(cl_team_detail, getString(R.string.message_error_favorite))
    }
}
