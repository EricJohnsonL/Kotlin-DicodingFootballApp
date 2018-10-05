package com.ericjohnson.footballapps.view.mainactivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ericjohnson.footballapps.R
import com.ericjohnson.footballapps.adapter.SchedulePagerAdapter
import com.ericjohnson.footballapps.utils.ScheduleType
import com.ericjohnson.footballapps.view.schedule.ScheduleFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    private var mainPresenter: IMainPresenter<MainView> = MainPresenter()

    private var titles: MutableList<String> = mutableListOf()

    private var fragment: MutableList<ScheduleFragment> = mutableListOf()

    private lateinit var schedulePagerAdapter: SchedulePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewPager()
        onAttachView()
    }

    private fun setupViewPager() {
        titles.add(getString(R.string.title_last_match))
        titles.add(getString(R.string.title_next_match))

        fragment.add(ScheduleFragment.newInstance(ScheduleType.PREVIOUS_EVENT))
        fragment.add(ScheduleFragment.newInstance(ScheduleType.NEXT_EVENT))

        schedulePagerAdapter = SchedulePagerAdapter(supportFragmentManager, titles, fragment)
        vp_match_schedule.adapter = schedulePagerAdapter
        vp_match_schedule.offscreenPageLimit = 0
        tab_match_schedule.setupWithViewPager(vp_match_schedule)
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
