package com.ericjohnson.footballapps.data.api.setup

import com.ericjohnson.footballapps.data.api.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by johnson on 04/10/18.
 */

interface ApiRepository {

    @GET("{type}.php")
    fun getMatches(@Path("type") type: String, @Query("id") leagueId: String): Call<MatchDetailResponse>

    @GET("lookupevent.php")
    fun getMatchDetail(@Query("id") matchId: String): Call<MatchDetailResponse>

    @GET("searchevents.php")
    fun getSearchedMatches(@Query("e") query: String): Call<SearchedMatchResponse>

    @GET("lookup_all_teams.php")
    fun getTeamList(@Query("id") leagueId: String): Call<TeamListResponse>

    @GET("lookupteam.php")
    fun getTeamDetail(@Query("id") teamId: String): Call<TeamDetailResponse>

    @GET("searchteams.php")
    fun getSearchedTeams(@Query("t") teamName: String): Call<TeamListResponse>

    @GET("lookup_all_players.php")
    fun getPlayerByTeam(@Query("id") teamId: String): Call<PlayerResponse>

    @GET("lookupplayer.php")
    fun getPlayerDetail(@Query("id") playerId: String): Call<PlayerDetailResponse>

    @GET("all_leagues.php")
    fun getLeague(): Call<LeagueResponse>
}