package com.realityexpander.cryptoapp.mappers

import com.realityexpander.cryptoapp.data.remote.dto.*
import com.realityexpander.cryptoapp.domain.models.Coin
import com.realityexpander.cryptoapp.domain.models.CoinInfo

fun CoinDTO.toCoin() = Coin(
    id = id,
    name = name,
    symbol = symbol,
    isActive = isActive,
    rank = rank,
)

fun Coin.toCoinDTO() = CoinDTO(
    id = id,
    name = name,
    symbol = symbol,
    isActive = isActive,
    rank = rank
)


fun CoinInfoDTO.toCoinInfo() = CoinInfo(
    coinId = id,
    name = name ,
    symbol = symbol,
    description = description,
    isActive = isActive,
    tags = tags.map { it.name }, // Want names of tags only
    teamMembers = teamMembers,
    type = type,
    whitepaper = whitepaper,
    linkCatalog = linkCatalog,
)

fun CoinInfo.toCoinInfoDTO() = CoinInfoDTO(
    id = coinId,
    name = name ,
    symbol = symbol,
    description = description,
    isActive = isActive,
    tags = tags.mapIndexed { i,name -> Tag(id=i.toString(), name = name) },
    teamMembers = teamMembers,
    type = type,
    whitepaper = whitepaper,
)

fun List<CoinDTO>.toCoins() = map { it.toCoin() }

fun List<Coin>.toCoinDTOs() = map { it.toCoinDTO() }
