package com.realityexpander.cryptoapp.data.remote.dto


import com.google.gson.annotations.SerializedName

data class CoinDTO(
    val id: String,
    @SerializedName("is_active")
    val isActive: Boolean = false,
    @SerializedName("is_new")
    val isNew: Boolean = false,
    val name: String = "",
    val rank: Int = 0,
    val symbol: String = "",
    val type: String = "coin",
)