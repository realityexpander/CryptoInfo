package com.realityexpander.cryptoapp.domain.models

import com.realityexpander.cryptoapp.data.remote.dto.*

data class CoinInfo(
    val coinId: String,
    val name: String,
    val symbol: String,
    val description: String = "",
    val isActive: Boolean = false,
    val tags: List<String> = emptyList(),
    val teamMembers: List<TeamMember> = emptyList(),
    val type: String = "Unknown",
    val whitepaper: Whitepaper = Whitepaper(),

    val developmentStatus: String = "",
    val firstDataAt: String = "",
    val hardwareWallet: Boolean = false,
    val hashAlgorithm: String  = "",
    val isNew: Boolean = false,
    val lastDataAt: String = "",
    val linkCatalog: LinkCatalog = LinkCatalog(),
    val linkCatalogExtendeds: List<LinkCatalogExtended> = listOf(),
    val message: String = "",
    val openSource: Boolean = false,
    val orgStructure: String = "",
    val proofType: String = "",
    val rank: Int = 0,
    val startedAt: String = "",
)