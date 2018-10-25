package com.ericjohnson.footballapps.view.matchescontainer

import com.ericjohnson.footballapps.base.IBasePresenter
import com.ericjohnson.footballapps.base.IBaseView

/**
 * Created by johnson on 10/18/18.
 */
interface IMatchesContainerPresenter<V : IBaseView> : IBasePresenter<V> {

    fun getLeague()
}