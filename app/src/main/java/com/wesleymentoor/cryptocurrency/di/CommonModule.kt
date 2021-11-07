package com.wesleymentoor.cryptocurrency.di

import com.wesleymentoor.cryptocurrency.common.dispatcher.Dispatcher
import com.wesleymentoor.cryptocurrency.common.dispatcher.DispatcherImpl
import com.wesleymentoor.cryptocurrency.data.remote.service.CoinPaprikaApi
import com.wesleymentoor.cryptocurrency.data.repository.CoinRepositoryImpl
import com.wesleymentoor.cryptocurrency.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Singleton
    @Provides
    fun provideDispatcherProvider(dispatcher: DispatcherImpl): Dispatcher = dispatcher

    @Singleton
    @Provides
    fun provideRepository(api: CoinPaprikaApi): CoinRepository = CoinRepositoryImpl(api)
}