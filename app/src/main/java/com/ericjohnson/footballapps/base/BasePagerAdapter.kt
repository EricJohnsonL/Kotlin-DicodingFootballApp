package com.ericjohnson.footballapps.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by johnson on 10/18/18.
 */
abstract class BasePagerAdapter(fm: FragmentManager?, private val fragments: List<Fragment>, private val titles: List<String>)
    : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = when {
        titles.size == fragments.size -> titles[position]
        else -> super.getPageTitle(position)
    }
}