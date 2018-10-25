package com.ericjohnson.footballapps.view.mainactivity

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView

import android.view.Menu
import android.view.MenuItem

import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.R.id.search
import com.ericjohnson.footballapps.utils.AppConstants
import com.ericjohnson.footballapps.utils.SearchType
import com.ericjohnson.footballapps.view.favoritecontainer.FavoriteContainer
import com.ericjohnson.footballapps.view.matchescontainer.MatchesContainer
import com.ericjohnson.footballapps.view.search.SearchActivity
import com.ericjohnson.footballapps.view.teamlist.TeamListFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity(), MainView {

    private var mainPresenter: IMainPresenter<MainView> = MainPresenter()

    private lateinit var currentFragment: Fragment

    private var isAbleToSearch: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onAttachView()
        setSupportActionBar(toolbar)
        setDefaultFragment()
        bottom_nav.setOnNavigationItemSelectedListener {
            when {
                it.itemId == R.id.menu_matches -> {
                    if (!it.isChecked)
                        loadFragment(MatchesContainer())
                    isAbleToSearch = true
                }
                it.itemId == R.id.menu_teams -> {
                    if (!it.isChecked)
                        loadFragment(TeamListFragment())
                    isAbleToSearch = true
                }
                it.itemId == R.id.menu_fav -> {
                    if (!it.isChecked)
                        loadFragment(FavoriteContainer())
                    isAbleToSearch = false
                }
            }
            invalidateOptionsMenu()
            true
        }

        bottom_nav.selectedItemId = R.id.menu_matches
    }

    private fun setDefaultFragment() {
        currentFragment = MatchesContainer()
        supportFragmentManager.beginTransaction().add(R.id.fl_container, currentFragment).commit()
    }

    private fun loadFragment(fragment: Fragment) {
        currentFragment = fragment
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_container, currentFragment)
            commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.search) {
            if (bottom_nav.selectedItemId == R.id.menu_matches) {
                startActivity<SearchActivity>(AppConstants.KEY_SEARCH_TYPE to SearchType.matches)

            } else if (bottom_nav.selectedItemId == R.id.menu_teams) {
                startActivity<SearchActivity>(AppConstants.KEY_SEARCH_TYPE to SearchType.teams)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {

        menu.findItem(search).isVisible = isAbleToSearch
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
    }

    override fun onAttachView() {
        mainPresenter.onAttach(this)
    }

    override fun onDetachView() {
        mainPresenter.onDetach()
    }
}
