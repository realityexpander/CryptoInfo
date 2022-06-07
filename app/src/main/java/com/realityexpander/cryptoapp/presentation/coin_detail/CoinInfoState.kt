package com.realityexpander.cryptoapp.presentation.coin_detail

import com.realityexpander.cryptoapp.domain.models.CoinInfo

data class CoinInfoState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val coinInfo: CoinInfo? = null
)
