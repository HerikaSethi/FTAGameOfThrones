package com.herika.flipkartassignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.herika.flipkartassignment.R
import com.herika.flipkartassignment.data.MatchResponse

// MatchAdapter.kt

class MatchAdapter(private val context: Context, private val playerId: Int) : RecyclerView.Adapter<MatchAdapter.MatchViewHolder>() {

    private var matches: List<MatchResponse> = emptyList()

    fun submitList(newList: List<MatchResponse>) {
        matches = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_match, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = matches[position]
        holder.bind(match)
    }

    override fun getItemCount(): Int {
        return matches.size
    }

    inner class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewOpponentName: TextView = itemView.findViewById(R.id.textViewOpponentName)
        private val textViewScores: TextView = itemView.findViewById(R.id.textViewScores)
        private val textViewResult: TextView = itemView.findViewById(R.id.textViewResult)

        fun bind(match: MatchResponse) {
            val opponentName = if (match.player1.id == playerId) match.player2.id else match.player1.id
            val opponentScore = if (match.player1.id == playerId) match.player2.score else match.player1.score
            val playerScore = if (match.player1.id == playerId) match.player1.score else match.player2.score

//            textViewOpponentName.text = opponentName
            textViewScores.text = "Player: $playerScore, Opponent: $opponentScore"
            textViewResult.text = determineResult(playerScore, opponentScore)
        }

        private fun determineResult(playerScore: Int, opponentScore: Int): String {
            return when {
                playerScore > opponentScore -> "Win"
                playerScore < opponentScore -> "Loss"
                else -> "Draw"
            }
        }
    }
}
