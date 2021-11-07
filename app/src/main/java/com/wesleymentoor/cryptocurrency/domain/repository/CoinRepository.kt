package com.wesleymentoor.cryptocurrency.domain.repository

import com.wesleymentoor.cryptocurrency.data.remote.dto.CoinDTO
import com.wesleymentoor.cryptocurrency.data.remote.dto.CoinDetailDTO
import kotlinx.coroutines.flow.Flow

interface CoinRepository {

    suspend fun getCoins(): Flow<List<CoinDTO>>

    suspend fun getCoinById(coinId: String): Flow<CoinDetailDTO>
}