package com.herika.flipkartassignment.repository

import com.herika.flipkartassignment.data.MatchResponse
import com.herika.flipkartassignment.data.Player
import com.herika.flipkartassignment.domainlayer.RetrofitInstance

class MatchRepository {
    suspend fun getMatchData(): List<MatchResponse> {
        return RetrofitInstance.api.getMatchData()
    }

    suspend fun getPlayerData(): List<Player> {
        return RetrofitInstance.api.getPlayerData()
    }
}
