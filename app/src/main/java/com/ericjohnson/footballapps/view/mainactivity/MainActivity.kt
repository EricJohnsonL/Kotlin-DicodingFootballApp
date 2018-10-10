package com.ericjohnson.footballapps.view.mainactivity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.utils.ScheduleType
import com.ericjohnson.footballapps.view.favorite.FavoriteMatchFragment
import com.ericjohnson.footballapps.view.schedule.ScheduleFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    private var mainPresenter: IMainPresenter<MainView> = MainPresenter()

    private lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setDefaultFragment()
        bottom_nav.setOnNavigationItemSelectedListener {
            when {
                it.itemId == R.id.menu_prev_match -> {
                    if (!it.isChecked)
                        loadFragment(ScheduleFragment.newInstance(ScheduleType.PREVIOUS_EVENT))
                }
                it.itemId == R.id.menu_next_match -> {
                    if (!it.isChecked)
                        loadFragment(ScheduleFragment.newInstance(ScheduleType.NEXT_EVENT))
                }
                it.itemId == R.id.menu_fav_match -> {
                    if (!it.isChecked)
                        loadFragment(FavoriteMatchFragment())
                }
            }
            true
        }

        onAttachView()
    }

    private fun setDefaultFragment() {
        currentFragment = ScheduleFragment.newInstance(ScheduleType.PREVIOUS_EVENT)
        supportFragmentManager.beginTransaction().add(R.id.fl_container, currentFragment).commit()
    }

    private fun loadFragment(fragment: Fragment) {
        currentFragment = fragment
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_container, currentFragment)
            commit()
        }
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
