package com.realityexpander.cryptoapp.presentation.coin_info

import com.realityexpander.cryptoapp.domain.models.CoinInfo

data class CoinInfoState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val coinInfo: CoinInfo = CoinInfo()
)
