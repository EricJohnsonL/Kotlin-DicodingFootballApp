package com.ericjohnson.footballapps.view.favoritecontainer.favoriteteam


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.adapter.recyclerviewadapter.FavoriteTeamAdapter
import com.ericjohnson.footballapps.data.api.TeamList
import com.ericjohnson.footballapps.data.db.FavoriteTeam
import com.ericjohnson.footballapps.utils.AppConstants
import com.ericjohnson.footballapps.view.teamdetail.TeamDetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavoriteTeamFragment : Fragment(), FavoriteTeamView, AnkoComponent<Context> {

    private val favoriteTeamPresenter: IFavoriteTeamPresenter<FavoriteTeamView> = FavoriteTeamPresenter()

    private lateinit var favoriteTeamAdapter: FavoriteTeamAdapter

    private lateinit var srlFavoriteTeam: SwipeRefreshLayout

    private lateinit var rvFavoriteTeam: RecyclerView

    private lateinit var llEmptyFavorite: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onAttachView()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }


    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        verticalLayout {
            lparams(width = matchParent, height = matchParent)

            srlFavoriteTeam = swipeRefreshLayout {

                relativeLayout {
                    rvFavoriteTeam = recyclerView {
                        lparams(width = matchParent, height = wrapContent)
                        visibility = View.GONE
                        hasFixedSize()
                        layoutManager = LinearLayoutManager(ctx)
                        favoriteTeamAdapter = FavoriteTeamAdapter(ctx, mutableListOf()) {
                            val teamList = TeamList(it.id, it.name, it.image)
                            ctx.startActivity<TeamDetailActivity>(AppConstants.KEY_TEAM_DETAIL to teamList)
                        }
                        adapter = favoriteTeamAdapter
                    }

                    llEmptyFavorite = verticalLayout {
                        gravity = Gravity.CENTER
                        visibility = View.GONE

                        imageView {
                            setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.ic_stars))
                        }.lparams(width = dip(48), height = dip(48))

                        textView {
                            textSize = 18f
                            textColor = Color.BLACK
                            text = context.getString(R.string.label_empty_fav_team)
                        }.lparams(width = wrapContent, height = wrapContent) {
                            topMargin = dip(8)
                        }
                    }.lparams(width = wrapContent, height = wrapContent) {
                        centerInParent()
                    }
                }
            }
        }
    }

    private fun getAllfavoriteTeams() {
        rvFavoriteTeam.visibility = View.GONE
        llEmptyFavorite.visibility = View.GONE
        favoriteTeamPresenter.getAllFavTeams(ctx)
    }


    override fun onResume() {
        super.onResume()
        getAllfavoriteTeams()
        srlFavoriteTeam.onRefresh {
            srlFavoriteTeam.isRefreshing = true
            getAllfavoriteTeams()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
    }


    override fun showAllFavTeams(favoriteTeam: MutableList<FavoriteTeam>) {
        srlFavoriteTeam.isRefreshing = false
        rvFavoriteTeam.visibility = View.VISIBLE
        favoriteTeamAdapter.addAllItem(favoriteTeam)
    }

    override fun showEmptyFavTeams() {
        rvFavoriteTeam.visibility = View.GONE
        srlFavoriteTeam.isRefreshing = false
        llEmptyFavorite.visibility = View.VISIBLE
    }

    override fun onAttachView() {
        favoriteTeamPresenter.onAttach(this)
    }

    override fun onDetachView() {
        favoriteTeamPresenter.onDetach()
    }

}
