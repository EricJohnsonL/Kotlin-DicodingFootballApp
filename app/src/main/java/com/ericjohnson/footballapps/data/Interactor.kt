package com.ericjohnson.footballapps.data

import android.content.Context
import com.ericjohnson.footballapps.data.api.League
import com.ericjohnson.footballapps.data.api.response.*
import com.ericjohnson.footballapps.data.api.setup.ApiRepository
import com.ericjohnson.footballapps.data.api.setup.ApiService
import com.ericjohnson.footballapps.data.db.FavoriteMatch
import com.ericjohnson.footballapps.data.db.FavoriteTeam
import com.ericjohnson.footballapps.data.db.setup.DbRepository
import com.ericjohnson.footballapps.data.db.setup.IDbRepository
import retrofit2.Call

/**
 * Created by johnson on 06/10/18.
 */
class Interactor {

    private val apiRepository: ApiRepository = ApiService.start()

    private val dbRepository: IDbRepository = DbRepository()

    fun getMatches(scheduleType: String, leagueId: String): Call<MatchDetailResponse> =
            apiRepository.getMatches(scheduleType, leagueId)

    fun getTeamDetail(teamId: String): Call<TeamDetailResponse> =
            apiRepository.getTeamDetail(teamId)

    fun getMatchDetail(eventId: String): Call<MatchDetailResponse> =
            apiRepository.getMatchDetail(eventId)

    fun getSearchedMatches(query: String): Call<SearchedMatchResponse> =
            apiRepository.getSearchedMatches(query)

    fun getTeamList(leagueId: String): Call<TeamListResponse> =
            apiRepository.getTeamList(leagueId)

    fun getSearchedTeams(teamName: String): Call<TeamListResponse> =
            apiRepository.getSearchedTeams(teamName)

    fun getPlayerByTeam(teamId: String): Call<PlayerResponse> =
            apiRepository.getPlayerByTeam(teamId)

    fun getPlayerDetail(playerId: String): Call<PlayerDetailResponse> =
            apiRepository.getPlayerDetail(playerId)

    fun getLeague(): Call<LeagueResponse> = apiRepository.getLeague()

    fun getAllFavMatches(context: Context): MutableList<FavoriteMatch> = dbRepository.getAllFavMatches(context)

    fun getFavMatch(context: Context, id: String): MutableList<FavoriteMatch> = dbRepository.getFavMatch(context, id)

    fun insertFavMatch(context: Context, favoriteMatch: FavoriteMatch): Boolean = dbRepository.insertFavMatch(context, favoriteMatch)

    fun removeFavMatch(context: Context, id: String): Boolean = dbRepository.removeFavMatch(context, id)

    fun getAllFavTeams(context: Context): MutableList<FavoriteTeam> = dbRepository.getAllFavTeams(context)

    fun getFavTeam(context: Context, id: String): MutableList<FavoriteTeam> = dbRepository.getFavTeam(context, id)

    fun insertFavTeam(context: Context, favoriteTeam: FavoriteTeam): Boolean = dbRepository.insertFavTeam(context, favoriteTeam)

    fun removeFavTeam(context: Context, id: String): Boolean = dbRepository.removeFavTeam(context, id)
}