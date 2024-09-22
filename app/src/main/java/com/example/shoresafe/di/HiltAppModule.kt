package com.example.shoresafe.di

import com.example.shoresafe.data.BeachWeatherRepository
import com.example.shoresafe.data.BeachWeatherRepositoryImpl
import com.example.shoresafe.network.BeachWeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://api.opencagedata.com/"
@Module
@InstallIn(SingletonComponent::class)
object HiltAppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideBeachWeatherApiService(retrofit: Retrofit): BeachWeatherApi {
        return retrofit.create(BeachWeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBeachWeatherRepository(placesSearchApiService: BeachWeatherApi): BeachWeatherRepository {
        return BeachWeatherRepositoryImpl()
    }
}