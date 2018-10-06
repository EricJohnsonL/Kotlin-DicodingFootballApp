package com.ericjohnson.footballapps.view.matchDetail

import com.ericjohnson.footballapps.base.IBaseView

/**
 * Created by johnson on 06/10/18.
 */

interface MatchDetailView : IBaseView {

    fun showHomeTeamBadge(imageUrl: String)

    fun showAwayTeamBadge(imageUrl: String)

    fun showError(message:String)
}