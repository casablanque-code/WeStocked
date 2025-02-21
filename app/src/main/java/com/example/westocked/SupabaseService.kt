package com.example.westocked

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable
import org.w3c.dom.Text


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
            // Игнорировать неизвестные поля в JSON
            json(Json { ignoreUnknownKeys =false })
        }
    }

    // Замените данные на свои:
    private val baseUrl = "https://jfqvygmcauhkibpvomjc.supabase.co/rest/v1"
    private val tableName = "equipment" // Имя вашей таблицы
    private val anonKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImpmcXZ5Z21jYXVoa2licHZvbWpjIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzgyMTE2MTEsImV4cCI6MjA1Mzc4NzYxMX0.GRLgecv63rchB-_w5c2Cmukk1KcZ8VF6ocTXzUDfnvk"

    // Функция выполняет GET-запрос и возвращает список оборудования
    suspend fun fetchEquipment(): List<Equipment> {
        val url = "$baseUrl/$tableName?select=*"
        return client.get(url) {
            header("apikey", anonKey)
            header("Authorization", "Bearer $anonKey")
        }.body()
    }

    fun close() {
        client.close()
    }
}
