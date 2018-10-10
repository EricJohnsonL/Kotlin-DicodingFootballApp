package com.ericjohnson.footballapps.data.api.setup

import com.ericjohnson.footballapps.data.api.response.MatchDetailResponse
import com.ericjohnson.footballapps.data.api.response.TeamDetailResponse
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

    @GET("lookupteam.php")
    fun getTeamDetail(@Query("id") teamId: String): Call<TeamDetailResponse>

    @GET("lookupevent.php")
    fun getMatchDetail(@Query("id") matchId: String): Call<MatchDetailResponse>
}