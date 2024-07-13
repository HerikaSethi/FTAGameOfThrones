package com.herika.flipkartassignment.domainlayer

import com.herika.flipkartassignment.data.MatchResponse
import com.herika.flipkartassignment.data.Player
import retrofit2.http.GET

interface ApiService {

    @GET("b/JNYL")
    suspend fun getMatchData(): List<MatchResponse>

    @GET("b/IKQQ")
    suspend fun getPlayerData(): List<Player>
}
