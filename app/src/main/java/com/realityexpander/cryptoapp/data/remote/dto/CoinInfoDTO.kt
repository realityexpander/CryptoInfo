package com.realityexpander.cryptoapp.data.remote.dto


import com.google.gson.annotations.SerializedName

// using the gson library to serialize the json data

data class CoinInfoDTO(
    val id: String,
    val description: String,
    @SerializedName("development_status") // note this annotation for gson library
    val developmentStatus: String = "",
    @SerializedName("first_data_at")
    val firstDataAt: String = "",
    @SerializedName("hardware_wallet")
    val hardwareWallet: Boolean = false,
    @SerializedName("hash_algorithm")
    val hashAlgorithm: String = "Unknown",
    @SerializedName("is_active")
    val isActive: Boolean = false,
    @SerializedName("is_new")
    val isNew: Boolean = false,
    @SerializedName("last_data_at")
    val lastDataAt: String = "",
    @SerializedName("links")
    val linkCatalog: LinkCatalog = LinkCatalog(),
    @SerializedName("links_extended")
    val linkCatalogExtendeds: List<LinkCatalogExtended> = listOf(),
    val message: String = "",
    val name: String = "",
    @SerializedName("open_source")
    val openSource: Boolean = false,
    @SerializedName("org_structure")
    val orgStructure: String = "",
    @SerializedName("proof_type")
    val proofType: String = "",
    val rank: Int = 0,
    @SerializedName("started_at")
    val startedAt: String = "",
    val symbol: String = "",
    val tags: List<Tag> = listOf(),
    @SerializedName("team")
    val teamMembers: List<TeamMember> = listOf(),
    val type: String = "",
    val whitepaper: Whitepaper = Whitepaper(),
)