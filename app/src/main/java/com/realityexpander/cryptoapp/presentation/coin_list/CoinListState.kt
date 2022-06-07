package com.realityexpander.cryptoapp.presentation.coin_list

import com.realityexpander.cryptoapp.domain.models.Coin

data class CoinListState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val coins: List<Coin> = emptyList()
)
