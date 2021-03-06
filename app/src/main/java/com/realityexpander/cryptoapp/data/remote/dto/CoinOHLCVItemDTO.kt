package com.realityexpander.cryptoapp.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias CoinOHLCVItemsDTO = ArrayList<CoinOHLCVItemDTO>

// Using the kotlinx library to serialize this json

@Serializable
data class CoinOHLCVItemDTO(
    val close: Double = 0.0,
    val high: Double = 0.0,
    val low: Double = 0.0,
    @SerialName("market_cap")  // note this annotation for kotlinx library
    val marketCap: Long = 0,
    val `open`: Double = 0.0,
    @SerialName("time_close")
    val timeClose: String? = null,
    @SerialName("time_open")
    val timeOpen: String? = null,
    val volume: Long = 0
)