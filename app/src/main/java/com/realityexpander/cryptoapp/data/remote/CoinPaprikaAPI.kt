package com.realityexpander.cryptoapp.data.remote

import com.realityexpander.cryptoapp.data.remote.dto.CoinDTO
import com.realityexpander.cryptoapp.data.remote.dto.CoinInfoDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinPaprikaAPI {
    @GET("v1/coins")
    suspend fun getCoinDTOs(): List<CoinDTO>

    @GET("v1/coins/{coinId}")
    suspend fun getCoinInfoDTOById(@Path("coinId") coinId: String): CoinInfoDTO

//    @GET("coins/{id}/market_chart/range/{start}/{end}")
//    fun getCoinHistory(@Path("id") id: String, @Path("start") start: String, @Path("end") end: String): Call<CoinHistory>

}