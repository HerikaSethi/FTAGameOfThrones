package com.herika.flipkartassignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.herika.flipkartassignment.adapter.MatchAdapter
import com.herika.flipkartassignment.repository.MatchRepository
import com.herika.flipkartassignment.viewmodel.MainViewModelFactory
import com.herika.flipkartassignment.viewmodel.MatchViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var matchViewModel: MatchViewModel
    private lateinit var matchAdapter: MatchAdapter
    private lateinit var matchesRecyclerView: RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val repository = MatchRepository()
        val viewModelFactory = MainViewModelFactory(repository)
        matchViewModel = ViewModelProvider(this, viewModelFactory).get(MatchViewModel::class.java)

        matchesRecyclerView = findViewById(R.id.rcv_matches)

        val playerId = intent.getIntExtra("playerId", -1)
        // Set up RecyclerView
        matchesRecyclerView.layoutManager = LinearLayoutManager(this)
        matchAdapter = MatchAdapter(this, playerId)
        matchesRecyclerView.adapter = matchAdapter


        if (playerId != -1) {
            val matches = matchViewModel.getMatchesForPlayer(playerId)
            matchAdapter.submitList(matches)
        }

    }
}
