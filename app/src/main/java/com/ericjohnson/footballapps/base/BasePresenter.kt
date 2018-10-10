package com.ericjohnson.footballapps.base

import com.ericjohnson.footballapps.data.Interactor

/**
 * Created by johnson on 04/10/18.
 */

open class BasePresenter<V : IBaseView> : IBasePresenter<V> {

    protected fun getInteractor(): Interactor = Interactor()

    protected var view: V? = null

    override fun onAttach(view: V) {
        this.view = view
    }

    override fun onDetach() {
        this.view = null
    }
}