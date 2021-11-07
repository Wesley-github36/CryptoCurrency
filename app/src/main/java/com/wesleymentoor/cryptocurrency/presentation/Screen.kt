package com.wesleymentoor.cryptocurrency.presentation

sealed class Screen(val route: String) {
    object CoinListScreen: Screen("coin_list")
    object CoinDetailsScreen: Screen("coin_detail")

}
