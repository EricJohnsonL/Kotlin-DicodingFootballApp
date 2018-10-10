package com.ericjohnson.footballapps.view.favorite


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
import com.ericjohnson.footballapps.adapter.FavoriteMatchAdapter
import com.ericjohnson.footballapps.data.db.FavoriteMatch
import com.ericjohnson.footballapps.utils.AppConstants
import com.ericjohnson.footballapps.view.matchDetail.MatchDetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavoriteMatchFragment : Fragment(), FavoriteMatchView, AnkoComponent<Context> {

    private lateinit var favoriteMatchAdapter: FavoriteMatchAdapter

    private lateinit var srlFavoriteMatch: SwipeRefreshLayout

    private lateinit var rvFavoriteMatch: RecyclerView

    private lateinit var llEmptyFavorite: LinearLayout

    private var favoriteMatchPresenter: IFavoriteMatchPresenter<FavoriteMatchView> =
            FavoriteMatchPresenter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        onAttachView()
    }

    override fun onDetach() {
        super.onDetach()
        onDetachView()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        verticalLayout {
            lparams(width = matchParent, height = matchParent)

            srlFavoriteMatch = swipeRefreshLayout {

                relativeLayout {
                    rvFavoriteMatch = recyclerView {
                        lparams(width = matchParent, height = wrapContent)
                        visibility = View.GONE
                        hasFixedSize()
                        layoutManager = LinearLayoutManager(ctx)
                        favoriteMatchAdapter = FavoriteMatchAdapter(ctx, mutableListOf()) {
                            ctx.startActivity<MatchDetailActivity>(AppConstants.KEY_MATCH_DETAIL to it.id)
                        }
                        adapter = favoriteMatchAdapter
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
                            text = context.getString(R.string.label_empty_fav_match)
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

    private fun getAllfavoriteMatches() {
        rvFavoriteMatch.visibility = View.GONE
        llEmptyFavorite.visibility = View.GONE
        favoriteMatchPresenter.getAllFavMatches(ctx)
    }


    override fun onResume() {
        super.onResume()
        getAllfavoriteMatches()
        srlFavoriteMatch.onRefresh {
            srlFavoriteMatch.isRefreshing = true
            getAllfavoriteMatches()
        }
    }

    override fun showAllFavMatches(favoriteMatches: MutableList<FavoriteMatch>) {
        srlFavoriteMatch.isRefreshing = false
        rvFavoriteMatch.visibility = View.VISIBLE
        favoriteMatchAdapter.addAllItem(favoriteMatches)
    }

    override fun showEmptyFavMatches() {
        rvFavoriteMatch.visibility = View.GONE
        srlFavoriteMatch.isRefreshing = false
        llEmptyFavorite.visibility = View.VISIBLE
    }

    override fun onAttachView() {
        favoriteMatchPresenter.onAttach(this)
    }

    override fun onDetachView() {
        favoriteMatchPresenter.onDetach()
    }

}
