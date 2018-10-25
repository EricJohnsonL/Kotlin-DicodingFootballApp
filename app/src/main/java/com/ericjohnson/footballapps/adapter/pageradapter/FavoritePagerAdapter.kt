package com.ericjohnson.footballapps.adapter.pageradapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.ericjohnson.footballapps.base.BasePagerAdapter

/**
 * Created by johnson on 10/23/18.
 */

class FavoritePagerAdapter(fm: FragmentManager?, fragments: List<Fragment>, titles: List<String>)
    : BasePagerAdapter(fm, fragments, titles)