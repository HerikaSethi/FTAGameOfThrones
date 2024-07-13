// MainActivity.kt
package com.herika.flipkartassignment

import PlayerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.herika.flipkartassignment.data.PlayerWithPoints
import com.herika.flipkartassignment.repository.MatchRepository
import com.herika.flipkartassignment.viewmodel.MainViewModelFactory
import com.herika.flipkartassignment.viewmodel.MatchViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MatchViewModel
    private lateinit var playersAdapter: PlayerAdapter
    private lateinit var recyclerViewPlayers: RecyclerView
    private lateinit var headingText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = MatchRepository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MatchViewModel::class.java)

        recyclerViewPlayers = findViewById(R.id.rcv_player)
        headingText = findViewById(R.id.points_screen)


        // Set up RecyclerView
        recyclerViewPlayers.layoutManager = LinearLayoutManager(this)
        playersAdapter = PlayerAdapter(this)
        recyclerViewPlayers.adapter = playersAdapter

        // Observe players with points data
        viewModel.playersWithPoints.observe(this, Observer { playersWithPoints ->
            // Update RecyclerView with player data
            playersAdapter.updateData(playersWithPoints)
        })
    }
}
