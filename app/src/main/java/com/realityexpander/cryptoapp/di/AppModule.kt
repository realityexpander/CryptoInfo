package com.realityexpander.cryptoapp.di

import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Build
import com.google.gson.Gson
import com.realityexpander.cryptoapp.BuildConfig
import com.realityexpander.cryptoapp.common.Constants
import com.realityexpander.cryptoapp.data.remote.CoinPaprikaAPI
import com.realityexpander.cryptoapp.data.remote.MockInterceptor
import com.realityexpander.cryptoapp.data.repository.CoinRepositoryImpl
import com.realityexpander.cryptoapp.domain.repository.CoinRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class) // Objects provided here will exist for the lifetime of the app
object AppModule {

    @Provides
    @Singleton
    @Named("MockOkHttpClient")
    fun provideMockOkHttpClient(
        @Named("MockInterceptor") mockInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(mockInterceptor)
        .build()

    @Provides
    @Singleton
    @Named("MockInterceptor")
    fun provideMockInterceptor(@ApplicationContext context: Context): Interceptor = MockInterceptor(context)

    @Provides
    @Singleton
    @Named("MockCoinPaprikaAPI")
    fun provideMockCoinPaprikaApi(
        @Named("MockOkHttpClient") client: OkHttpClient
    ): CoinPaprikaAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinPaprikaAPI::class.java)
    }

    @Provides
    @Singleton  // Only one instance of this class will be created
    @Named("CoinPaprikaAPI")
    fun provideCoinPaprikaApi(): CoinPaprikaAPI {  // uses OkHttpClient by default
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinPaprikaAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(
        @Named("CoinPaprikaAPI") coinPaprikaAPI: CoinPaprikaAPI,
        @Named("MockCoinPaprikaAPI") mockCoinPaprikaAPI: CoinPaprikaAPI
    ): CoinRepositoryInterface {

        if (BuildConfig.MOCK_MODE) {
            return CoinRepositoryImpl(mockCoinPaprikaAPI)
        }

        return CoinRepositoryImpl(coinPaprikaAPI)
    }
}