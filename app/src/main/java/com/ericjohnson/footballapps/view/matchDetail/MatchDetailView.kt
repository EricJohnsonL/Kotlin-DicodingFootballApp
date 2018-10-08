package com.ericjohnson.footballapps.view.matchDetail

import com.ericjohnson.footballapps.base.IBaseView
import com.ericjohnson.footballapps.data.api.MatchDetail

/**
 * Created by johnson on 06/10/18.
 */

interface MatchDetailView : IBaseView {

    fun showMatchDetail(matchDetail: MatchDetail)

    fun showErrorMatchDetail();

    fun showHomeTeamBadge(imageUrl: String)

    fun showAwayTeamBadge(imageUrl: String)

    fun showError(message: String)
}