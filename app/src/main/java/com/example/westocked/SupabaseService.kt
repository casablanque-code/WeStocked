package com.example.westocked

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable

@Serializable
data class Equipment(
    val inventory_number: Long,
    val name: String,
    val location: String,
    val serial_number: String,
    val mac_address: String
)

class SupabaseService {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    // URL и ключи:
    private val baseUrl = "https://jfqvygmcauhkibpvomjc.supabase.co/rest/v1"
    private val tableName = "equipment"
    private val anonKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImpmcXZ5Z21jYXVoa2licHZvbWpjIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzgyMTE2MTEsImV4cCI6MjA1Mzc4NzYxMX0.GRLgecv63rchB-_w5c2Cmukk1KcZ8VF6ocTXzUDfnvk"

    suspend fun fetchEquipment(): List<Equipment> {
        val url = "$baseUrl/$tableName?select=*"
        return client.get(url) {
            header("apikey", anonKey)
            header("Authorization", "Bearer $anonKey")
        }.body()
    }

    suspend fun updateEquipment(equipment: Equipment): Boolean {
        val response: HttpResponse = client.patch("$baseUrl/$tableName") {
            header("apikey", anonKey)
            header("Authorization", "Bearer $anonKey")
            header("Content-Type", "application/json")
            parameter("inventory_number", "eq.${equipment.inventory_number}")
            setBody(Json.encodeToString(Equipment.serializer(), equipment))
        }
        return response.status == HttpStatusCode.NoContent
    }

    suspend fun addEquipment(equipment: Equipment): Boolean {
        // POST-запрос для добавления нового оборудования
        val response: HttpResponse = client.post("$baseUrl/$tableName") {
            header("apikey", anonKey)
            header("Authorization", "Bearer $anonKey")
            header("Content-Type", "application/json")
            setBody(Json.encodeToString(Equipment.serializer(), equipment))
        }
        // Ждем успешный статус "201 Created". Можно проверить в postman
        return response.status == HttpStatusCode.Created
    }

    fun close() {
        client.close()
    }
}
