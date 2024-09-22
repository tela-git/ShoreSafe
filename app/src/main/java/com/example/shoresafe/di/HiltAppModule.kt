package com.example.shoresafe.di

import com.example.shoresafe.data.PlaceSearchRepository
import com.example.shoresafe.data.PlacesSearchRepoImpl
import com.example.shoresafe.network.PlaceSearchApi
import com.example.weathersamachar.data.model.PlacesSearchResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.intellij.lang.annotations.PrintFormat
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
    fun provideApiService(retrofit: Retrofit): PlaceSearchApi {
        return retrofit.create(PlaceSearchApi::class.java)
    }

    @Provides
    @Singleton
    fun providePlacesSearchRepository(placesSearchApiService: PlaceSearchApi): PlaceSearchRepository {
        return PlacesSearchRepoImpl(
            placesSearchApiService
        )
    }
}