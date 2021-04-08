package com.example.sportsrecordapp.network.entity

import com.example.sportsrecordapp.local.model.SportEventResult
import com.example.sportsrecordapp.local.model.SportType
import com.google.gson.annotations.SerializedName

class SportsRecordsResponse(
    val f1Results: List<F1Result>?,
    val nbaResults: List<NbaResult>?,
    @SerializedName("Tennis")
    val tennis: List<Tennis>?
) {
    data class F1Result(
        val publicationDate: String?,
        val seconds: Double?,
        val tournament: String?,
        val winner: String?
    )

    data class NbaResult(
        val gameNumber: Long?,
        val looser: String?,
        val mvp: String?,
        val publicationDate: String?,
        val tournament: String?,
        val winner: String?
    )

    data class Tennis(
        val looser: String?,
        val numberOfSets: Long?,
        val publicationDate: String?,
        val tournament: String?,
        val winner: String?
    )
}

fun SportsRecordsResponse.toSportEventsResultList(): List<SportEventResult> {

    val eventsResultList = mutableListOf<SportEventResult>()

    eventsResultList.addAll(nbaResults?.map {
        SportEventResult(
            winner = it.winner.orEmpty(),
            looser = it.looser,
            tournament = it.tournament.orEmpty(),
            publicationDate = it.publicationDate.orEmpty(),
            sportType = SportType.NBA
        )
    } ?: emptyList())

    eventsResultList.addAll(tennis?.map {
        SportEventResult(
            winner = it.winner.orEmpty(),
            looser = it.looser,
            tournament = it.tournament.orEmpty(),
            publicationDate = it.publicationDate.orEmpty(),
            sportType = SportType.TENNIS
        )
    } ?: emptyList())

    eventsResultList.addAll(f1Results?.map {
        SportEventResult(
            winner = it.winner.orEmpty(),
            looser = null,
            tournament = it.tournament.orEmpty(),
            publicationDate = it.publicationDate.orEmpty(),
            sportType = SportType.F1
        )
    } ?: emptyList())

    // This is just temporal to mix up a little bit all the events
    // TODO: Create a DateHelper class to transform the response date into something we can work with
    eventsResultList.shuffle()
    return eventsResultList
}