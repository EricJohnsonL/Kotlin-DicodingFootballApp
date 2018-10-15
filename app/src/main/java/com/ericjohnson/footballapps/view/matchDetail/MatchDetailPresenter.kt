package com.ericjohnson.footballapps.view.matchDetail

import android.content.Context
import com.ericjohnson.footballapps.base.BasePresenter
import com.ericjohnson.footballapps.data.api.MatchDetail
import com.ericjohnson.footballapps.data.api.response.TeamDetailResponse
import com.ericjohnson.footballapps.data.db.FavoriteMatch
import com.ericjohnson.footballapps.domain.GetAwayTeamBadgeInteractor
import com.ericjohnson.footballapps.domain.GetHomeTeamBadgeInteractor
import com.ericjohnson.footballapps.domain.GetMatchDetailInteractor

/**
 * Created by johnson on 06/10/18.
 */

class MatchDetailPresenter<V : MatchDetailView> : BasePresenter<V>(), IMatchDetailPresenter<V>,
        GetHomeTeamBadgeInteractor.GetHomeTeamBadgeInteractorListener, GetAwayTeamBadgeInteractor.GetAwayTeamBadgeInteractorListener, GetMatchDetailInteractor.GetMatchDetailInteractorListener {

    private val getHomeTeamBadgeInteractor: GetHomeTeamBadgeInteractor = GetHomeTeamBadgeInteractor(this)

    private val getAwayTeamBadgeInteractor: GetAwayTeamBadgeInteractor = GetAwayTeamBadgeInteractor(this)

    private val getMatchDetailInteractor: GetMatchDetailInteractor = GetMatchDetailInteractor(this)

    //region Home Team Badge
    override fun getHomeTeamBadge(teamId: String) {
        getHomeTeamBadgeInteractor.teamId = teamId
        getHomeTeamBadgeInteractor.call()
    }

    override fun onGetHomeTeamBadgeSuccess(teamDetailResponse: TeamDetailResponse) {
        view?.showHomeTeamBadge(teamDetailResponse.teams[0].strTeamBadge.toString())
    }

    override fun onGetHomeTeamBadgeFailed(message: String) {
        view?.showImageError(message)
    }
    //endregion

    //region Away Team Badge
    override fun getAwayTeamBadge(teamId: String) {
        getAwayTeamBadgeInteractor.teamId = teamId
        getAwayTeamBadgeInteractor.call()
    }

    override fun onGetAwayTeamBadgeSuccess(teamDetailResponse: TeamDetailResponse) {
        view?.showAwayTeamBadge(teamDetailResponse.teams[0].strTeamBadge.toString())
    }

    override fun onGetAwayTeamBadgeFailed(message: String) {
        view?.showImageError(message)
    }
    //endregion

    //region Match Detail
    override fun getMatchDetail(eventId: String) {
        getMatchDetailInteractor.eventId = eventId
        getMatchDetailInteractor.call()
    }

    override fun onGetMatchDetailSuccess(matchDetail: MatchDetail) {
        view?.showMatchDetail(matchDetail)
    }

    override fun onGetMatchDetailFailed() {
        view?.showErrorMatchDetail()
    }
    //endregion

    //region Favorites

    override fun checkFavorites(context: Context, id: String) {
        if (getInteractor().getFavMatch(context, id).isEmpty()) {
            view?.checkIsFavorite(false)
        } else {
            view?.checkIsFavorite(true)
        }
    }

    override fun setToFavorites(context: Context, favoriteMatch: FavoriteMatch) {
        if (getInteractor().insertFavMatch(context, favoriteMatch)) {
            view?.addTofavoriteSuccess()
        } else {
            view?.addOrRemoveFavoriteFailed()
        }
    }

    override fun removeFromFavorites(context: Context, id: String) {
        if (getInteractor().removeFavMatch(context, id)) {
            view?.removeFromFavoriteSuccess()
        } else {
            view?.addOrRemoveFavoriteFailed()
        }
    }
    //endregion

}