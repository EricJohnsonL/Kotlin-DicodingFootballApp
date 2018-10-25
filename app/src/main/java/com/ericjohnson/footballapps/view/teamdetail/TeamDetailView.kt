package com.ericjohnson.footballapps.view.teamdetail

import com.ericjohnson.footballapps.base.IBaseView

/**
 * Created by johnson on 10/22/18.
 */
interface TeamDetailView : IBaseView {

    fun checkIsFavorite(isFavorite: Boolean)

    fun addTofavoriteSuccess()

    fun removeFromFavoriteSuccess()

    fun addOrRemoveFavoriteFailed()
}