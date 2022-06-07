package com.realityexpander.cryptoapp.di

import com.google.gson.Gson
import com.realityexpander.cryptoapp.common.Constants
import com.realityexpander.cryptoapp.data.remote.CoinPaprikaAPI
import com.realityexpander.cryptoapp.data.repository.CoinRepositoryImpl
import com.realityexpander.cryptoapp.domain.repository.CoinRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Objects provided here will exist for the lifetime of the app
object AppModule {

    @Provides
    @Singleton  // Only one instance of this class will be created
    fun provideCoinPaprikaApi(): CoinPaprikaAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinPaprikaAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(coinPaprikaAPI: CoinPaprikaAPI): CoinRepositoryInterface {
        return CoinRepositoryImpl(coinPaprikaAPI)
    }
}