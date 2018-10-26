package com.ericjohnson.footballapps.view.favoritecontainer


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.adapter.pageradapter.FavoritePagerAdapter
import com.ericjohnson.footballapps.view.favoritecontainer.favoritematch.FavoriteMatchFragment
import com.ericjohnson.footballapps.view.favoritecontainer.favoriteteam.FavoriteTeamFragment
import kotlinx.android.synthetic.main.fragment_favorite_container.*
import org.jetbrains.anko.support.v4.onPageChangeListener

class FavoriteContainer : Fragment(), FavoriteContainerView {

    private val presenter: IFavoriteContainerPresenter<FavoriteContainerView> = FavoriteContainerPresenter()

    private var titles: MutableList<String> = mutableListOf()

    private var fragment: MutableList<Fragment> = mutableListOf()

    private lateinit var favoritePagerAdapter: FavoritePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onAttachView()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    override fun onDestroy() {
        onDetachView()
        super.onDestroy()
    }

    private fun setupViewPager() {
        titles.add(getString(R.string.title_fav_match))
        titles.add(getString(R.string.title_fav_team))

        fragment.add(FavoriteMatchFragment())
        fragment.add(FavoriteTeamFragment())

        favoritePagerAdapter = FavoritePagerAdapter(fragmentManager, fragment, titles)
        vp_favorite.adapter = favoritePagerAdapter
        vp_favorite.offscreenPageLimit = 0
        tab_favorite.setupWithViewPager(vp_favorite)
    }

    override fun onAttachView() {
        presenter.onAttach(this)
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

}
