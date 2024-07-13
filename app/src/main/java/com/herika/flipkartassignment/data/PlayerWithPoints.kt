package com.herika.flipkartassignment.data

data class PlayerWithPoints(
    val id: Int,
    val name: String,
    val icon: String,
    var points: Int = 0
)
