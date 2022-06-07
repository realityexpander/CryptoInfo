package com.realityexpander.cryptoapp.data.remote.dto


import com.google.gson.annotations.SerializedName

data class LinkCatalog(
    @SerializedName("explorer")
    val explorers: List<String> = listOf(),
    @SerializedName("facebook")
    val facebooks: List<String> = listOf(),
    @SerializedName("reddit")
    val reddits: List<String> = listOf(),
    @SerializedName("source_code")
    val sourceCodes: List<String> = listOf(),
    @SerializedName("websites")
    val websites: List<String> = listOf(),
    @SerializedName("youtube")
    val youtubes: List<String> = listOf()
)