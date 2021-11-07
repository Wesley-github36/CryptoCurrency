package com.wesleymentoor.cryptocurrency.data.remote.dto

import com.squareup.moshi.Json
import com.wesleymentoor.cryptocurrency.domain.model.Coin

data class CoinDTO(
    val id: String,
    @Json(name = "is_active")
    val isActive: Boolean,
    @Json(name = "is_new")
    val isNew: Boolean,
    val name: String,
    val rank: Int,
    val symbol: String,
    val type: String
)

/**
 * Convert a list of CoinDTOs to a list of Coins
 */
fun List<CoinDTO>.asDomainModel(): List<Coin> {
    return map {
        Coin(
            id = it.id,
            isActive = it.isActive,
            name = it.name,
            rank = it.rank,
            symbol = it.symbol
        )
    }
}