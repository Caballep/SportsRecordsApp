package com.example.sportsrecordapp.network.entity

import com.example.sportsrecordapp.local.model.SportEventResult
import com.example.sportsrecordapp.local.model.SportType

class SportsRecordsResponse(
    val f1Results: List<F1Result>,
    val nbaResults: List<NbaResult>,
    val tennis: List<Tennis>
) {
    data class F1Result(
        val publicationDate: String,
        val seconds: Double,
        val tournament: String,
        val winner: String
    )

    data class NbaResult(
        val gameNumber: Long,
        val looser: String,
        val mvp: String,
        val publicationDate: String,
        val tournament: String,
        val winner: String
    )

    data class Tennis(
        val looser: String,
        val numberOfSets: Long,
        val publicationDate: String,
        val tournament: String,
        val winner: String
    )
}

fun SportsRecordsResponse.toSportEventsResultList(): List<SportEventResult> {

    val eventsResultList = mutableListOf<SportEventResult>()

    eventsResultList.addAll(nbaResults.map {
        SportEventResult(
            winner = it.winner,
            looser = it.looser,
            tournament = it.tournament,
            publicationDate = it.publicationDate,
            sportType = SportType.NBA
        )
    })

    eventsResultList.addAll(tennis.map {
        SportEventResult(
            winner = it.winner,
            looser = it.looser,
            tournament = it.tournament,
            publicationDate = it.publicationDate,
            sportType = SportType.TENNIS
        )
    })

    eventsResultList.addAll(f1Results.map {
        SportEventResult(
            winner = it.winner,
            looser = null,
            tournament = it.tournament,
            publicationDate = it.publicationDate,
            sportType = SportType.F1
        )
    })

    // This is just temporal to mix up a little bit all the events
    // TODO: Create a DateHelper class to transform the response date into something we can work with
    eventsResultList.shuffle()
    return eventsResultList
}