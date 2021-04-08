package com.example.sportsrecordapp.network.service

import com.example.sportsrecordapp.network.entity.SportsRecordsResponse
import retrofit2.http.Headers
import retrofit2.http.POST

interface SportsRecordsService {

    @POST("/results")
    @Headers("Content-Type: application/json")
    suspend fun getSportsRecords(): SportsRecordsResponse
}