package com.ericjohnson.footballapps.base

/**
 * Created by johnson on 04/10/18.
 */
interface IBasePresenter<T : IBaseView> {

    fun onAttach(view: T)

    fun onDetach()
}