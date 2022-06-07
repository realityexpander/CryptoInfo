package com.realityexpander.cryptoapp.data.repository

import com.realityexpander.cryptoapp.data.remote.CoinPaprikaAPI
import com.realityexpander.cryptoapp.data.remote.dto.CoinDTO
import com.realityexpander.cryptoapp.data.remote.dto.CoinInfoDTO
import com.realityexpander.cryptoapp.domain.repository.CoinRepositoryInterface
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val coinApi: CoinPaprikaAPI
) : CoinRepositoryInterface {

    override suspend fun getCoinDTOs(): List<CoinDTO> {
        return coinApi.getCoinDTOs()
    }

    override suspend fun getCoinInfoDTO(coinId: String): CoinInfoDTO {
        return coinApi.getCoinInfoDTOById(coinId)
    }
}
