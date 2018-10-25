package com.ericjohnson.footballapps.view.playerdetail

import com.ericjohnson.footballapps.base.IBasePresenter
import com.ericjohnson.footballapps.base.IBaseView

/**
 * Created by johnson on 10/23/18.
 */
interface IPlayerDetailPresenter<V:IBaseView>: IBasePresenter<V> {

    fun getPlayerDetail(playerId:String)
}