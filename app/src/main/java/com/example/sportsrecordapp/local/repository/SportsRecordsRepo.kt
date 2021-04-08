package com.example.sportsrecordapp.local.repository

import com.example.sportsrecordapp.network.entity.SportsRecordsResponse
import com.example.sportsrecordapp.network.service.SportsRecordsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SportsRecordsRepo @Inject constructor(
    private val sportsRecordsService: SportsRecordsService
) {
    suspend fun getSportsRecords(): Flow<SportsRecordsResponse> {
        return flow {
            emit(
                sportsRecordsService.getSportsRecords()
            )
        }.flowOn(Dispatchers.IO)
    }
}