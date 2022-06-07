package com.realityexpander.cryptoapp.domain.repository

import com.realityexpander.cryptoapp.data.remote.dto.CoinDTO
import com.realityexpander.cryptoapp.data.remote.dto.CoinInfoDTO

// Domain layer contains the interfaces for the data layer
interface CoinRepositoryInterface {

    suspend fun getCoinDTOs(): List<CoinDTO>

    suspend fun getCoinInfoDTO(coinId: String): CoinInfoDTO
}