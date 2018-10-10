package com.ericjohnson.footballapps.view.favorite

import android.content.Context
import com.ericjohnson.footballapps.base.IBasePresenter
import com.ericjohnson.footballapps.base.IBaseView

/**
 * Created by johnson on 10/10/18.
 */
interface IFavoriteMatchPresenter<V : IBaseView> : IBasePresenter<V> {

    fun getAllFavMatches(context: Context)
}