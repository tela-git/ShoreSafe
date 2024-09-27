package com.example.shoresafe.data

import com.example.shoresafe.data.model.beachsearch.Beach
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

interface BeachSearchRepository {
    suspend fun listAllBeaches(): List<Beach>

    suspend fun queryBeaches(query: String): List<Beach>
}

class BeachSearchRepositoryImpl(
    private val supabase: SupabaseClient
): BeachSearchRepository {
    override suspend fun listAllBeaches(): List<Beach> {
        return supabase.from("beaches").select().decodeList<Beach>()
    }

    override suspend fun queryBeaches(query: String): List<Beach> {
        return supabase.from(table = "beaches").select {
            filter {
                or {
                    ilike("name", "%$query%")
                    ilike("city", "%$query%")
                    ilike("state","%$query%")
                }
            }
        }
            .decodeList<Beach>()
    }
}
