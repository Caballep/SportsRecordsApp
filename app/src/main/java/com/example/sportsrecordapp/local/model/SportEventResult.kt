package com.example.sportsrecordapp.local.model

data class SportEventResult(
    val winner: String,
    val looser: String?,
    val tournament: String,
    val publicationDate: String,
    val sportType: SportType
)