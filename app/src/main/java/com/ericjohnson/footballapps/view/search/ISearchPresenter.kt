package com.ericjohnson.footballapps.view.search

import com.ericjohnson.footballapps.base.IBasePresenter
import com.ericjohnson.footballapps.base.IBaseView

/**
 * Created by johnson on 10/24/18.
 */
interface ISearchPresenter<V : IBaseView> : IBasePresenter<V> {

    fun getTeam(query:String)

    fun getMathes(query: String)
}