package com.example.shoresafe.di

import com.example.shoresafe.data.BeachSearchRepository
import com.example.shoresafe.data.BeachSearchRepositoryImpl
import com.example.shoresafe.data.BeachWeatherRepository
import com.example.shoresafe.data.BeachWeatherRepositoryImpl
import com.example.shoresafe.network.BeachWeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://marine-api.open-meteo.com/"
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
    fun provideSupabaseClient(): SupabaseClient {
        val supabase = createSupabaseClient(
            supabaseUrl = "https://goulnxubcfwwgxynpqlb.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImdvdWxueHViY2Z3d2d4eW5wcWxiIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjcwNjc0NDUsImV4cCI6MjA0MjY0MzQ0NX0.VbB6OigODlyePBRAeXl3pHWMnUlEQUppIgP2ohYWMZo"
        ) {
            defaultSerializer = KotlinXSerializer(json = Json)
            install(Postgrest)
        }
        return supabase
    }

    @Provides
    @Singleton
    fun provideBeachWeatherApiService(retrofit: Retrofit): BeachWeatherApi {
        return retrofit.create(BeachWeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBeachWeatherRepository(beachWeatherApiService: BeachWeatherApi): BeachWeatherRepository {
        return BeachWeatherRepositoryImpl(
            beachWeatherApi = beachWeatherApiService
        )
    }

    @Provides
    @Singleton
    fun proviceBeachSearchRepository(supabase: SupabaseClient) :BeachSearchRepository{
        return BeachSearchRepositoryImpl(supabase)
    }
}