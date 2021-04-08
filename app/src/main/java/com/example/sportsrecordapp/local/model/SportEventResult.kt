package com.example.sportsrecordapp.local.model

// This model design is arbitrary, it is meant to represent all types of incoming sports
data class SportEventResult(
    val winner: String,
    val looser: String?,
    val tournament: String,
    val publicationDate: String,
    val sportType: SportType
)