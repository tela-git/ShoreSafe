package com.example.shoresafe.data

import com.example.shoresafe.data.model.Beach
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

interface BeachSearchRepository {
    suspend fun listAllBeaches(): List<Beach>
}

class BeachSearchRepositoryImpl(
    private val supabase: SupabaseClient
): BeachSearchRepository {
    override suspend fun listAllBeaches(): List<Beach> {
        return supabase.from("beaches").select().decodeList<Beach>()
    }
}
