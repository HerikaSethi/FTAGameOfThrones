// MatchViewModel.kt
package com.herika.flipkartassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herika.flipkartassignment.data.MatchResponse
import com.herika.flipkartassignment.data.Player
import com.herika.flipkartassignment.data.PlayerWithPoints
import com.herika.flipkartassignment.repository.MatchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MatchViewModel(private val repository: MatchRepository) : ViewModel() {

    private val _playersWithPoints = MutableLiveData<List<PlayerWithPoints>>()
    val playersWithPoints: LiveData<List<PlayerWithPoints>>
        get() = _playersWithPoints

    private val _matches = MutableLiveData<List<MatchResponse>>()
    val matches: LiveData<List<MatchResponse>>
        get() = _matches

    init {
        fetchPlayersWithPoints()
        fetchMatches()
    }

     fun fetchMatches() {
        viewModelScope.launch {
            val matches = withContext(Dispatchers.IO) {
                repository.getMatchData()
            }
            _matches.postValue(matches)
        }
    }

    fun getMatchesForPlayer(playerId: Int): List<MatchResponse> {
        return matches.value?.filter { it.player1.id == playerId || it.player2.id == playerId } ?: emptyList()
    }

    private fun fetchPlayersWithPoints() {
        viewModelScope.launch {
            val players = withContext(Dispatchers.IO) {
                repository.getPlayerData()
            }

            val matchData = withContext(Dispatchers.IO) {
                repository.getMatchData()
            }


            val playersWithPoints = calculatePlayersWithPoints(players, matchData)
            // Sort by points in descending order
            val sortedPlayers = playersWithPoints.sortedByDescending { it.points }
            _playersWithPoints.postValue(sortedPlayers)
        }
    }

    private fun calculatePlayersWithPoints(players: List<Player>, matches: List<MatchResponse>): List<PlayerWithPoints> {
        val playerPointsMap = mutableMapOf<Int, Int>()

        matches.forEach { match ->
            val player1 = match.player1
            val player2 = match.player2

            // player 1 is the winner
            if (player1.score > player2.score) {
                playerPointsMap[player1.id] = playerPointsMap.getOrDefault(player1.id, 0) + 3
                playerPointsMap[player2.id] = playerPointsMap.getOrDefault(player2.id, 0)
            } else if (player1.score < player2.score) {
                // player 2 is the winner
                playerPointsMap[player2.id] = playerPointsMap.getOrDefault(player2.id, 0) + 3
                playerPointsMap[player1.id] = playerPointsMap.getOrDefault(player1.id, 0)
            } else {
                // match is tie
                playerPointsMap[player1.id] = playerPointsMap.getOrDefault(player1.id, 0) + 1
                playerPointsMap[player2.id] = playerPointsMap.getOrDefault(player2.id, 0) + 1
            }
        }

        // Map players with their points
        return players.map { player ->
            val points = playerPointsMap[player.id] ?: 0
            PlayerWithPoints(player.id, player.name, player.icon, points)
        }
    }




}
