package com.ericjohnson.footballapps.view.favoritecontainer

import com.ericjohnson.footballapps.base.BasePresenter

/**
 * Created by johnson on 10/22/18.
 */
class FavoriteContainerPresenter <V : FavoriteContainerView> : BasePresenter<V>(),
        IFavoriteContainerPresenter<V>