package com.realityexpander.cryptoapp.common

fun Double.roundToDecimalPlaces(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return kotlin.math.round(this * multiplier) / multiplier
}