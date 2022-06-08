package com.realityexpander.cryptoapp.presentation

// Defines the names of the routes
sealed class Screen( val route: String) {
    object CoinListScreen : Screen("coin_list_screen")
    object CoinInfoScreen : Screen("coin_info_screen")
}
