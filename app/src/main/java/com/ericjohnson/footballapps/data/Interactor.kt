package com.ericjohnson.footballapps.data

import com.ericjohnson.footballapps.data.api.MatchDetail
import com.ericjohnson.footballapps.data.api.response.MatchDetailResponse
import com.ericjohnson.footballapps.data.api.response.TeamDetailResponse
import com.ericjohnson.footballapps.data.api.setup.ApiRepository
import com.ericjohnson.footballapps.data.api.setup.ApiService
import retrofit2.Call

/**
 * Created by johnson on 06/10/18.
 */
class Interactor {

    private val apiRepository: ApiRepository = ApiService.start()

    fun getMatches(scheduleType: String, leagueId: String): Call<MatchDetailResponse> =
            apiRepository.getMatches(scheduleType, leagueId)

    fun getTeamDetail(teamId: String): Call<TeamDetailResponse> =
            apiRepository.getTeamDetail(teamId)

    fun getMatchDetail(eventId:String):Call<MatchDetailResponse> =
            apiRepository.getMatchDetail(eventId)

}