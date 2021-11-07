package com.wesleymentoor.cryptocurrency.data.repository

import com.wesleymentoor.cryptocurrency.data.remote.dto.CoinDTO
import com.wesleymentoor.cryptocurrency.data.remote.dto.CoinDetailDTO
import com.wesleymentoor.cryptocurrency.data.remote.service.CoinPaprikaApi
import com.wesleymentoor.cryptocurrency.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinPaprikaApi
): CoinRepository {
    override suspend fun getCoins(): Flow<List<CoinDTO>> = flow {
        emit(api.getCoins())
    }

    override suspend fun getCoinById(coinId: String): Flow<CoinDetailDTO> = flow {
        emit(api.getCoinById(coinId))
    }

}