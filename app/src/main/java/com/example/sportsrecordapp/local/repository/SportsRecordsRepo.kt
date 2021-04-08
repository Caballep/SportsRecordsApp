package com.example.sportsrecordapp.local.repository

import com.example.sportsrecordapp.network.entity.SportsRecordsResponse
import com.example.sportsrecordapp.network.service.SportsRecordsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SportsRecordsRepo @Inject constructor(
    private val sportsRecordsService: SportsRecordsService
) {
    suspend fun getSportsRecords(): Flow<SportsRecordsResponse> {
        return flow {
            // The purpose of this app is to show off, is not meant to go out to Production
            // The following delay is just to make the Loading State handling more visual
            delay(2000)
            emit(
                sportsRecordsService.getSportsRecords()
            )
        }.flowOn(Dispatchers.IO)
    }
}