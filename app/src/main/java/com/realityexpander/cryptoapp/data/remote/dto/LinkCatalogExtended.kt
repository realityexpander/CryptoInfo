package com.realityexpander.cryptoapp.data.remote.dto


import com.google.gson.annotations.SerializedName

data class LinkCatalogExtended(
    @SerializedName("stats")
    val statistic: Stats,
    val type: String,
    val url: String
)