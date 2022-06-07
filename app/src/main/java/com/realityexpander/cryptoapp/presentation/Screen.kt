package com.realityexpander.cryptoapp.presentation

sealed class Screen( val route: String) {
    object CoinListScreen : Screen("coin_list_screen")
    object CoinInfoScreen : Screen("coin_Info_screen")
}
