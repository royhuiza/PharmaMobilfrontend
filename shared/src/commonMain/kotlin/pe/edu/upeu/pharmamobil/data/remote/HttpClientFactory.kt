package pe.edu.upeu.pharmamobil.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.logging.Logger

fun crearHttpClient(
    engine: HttpClientEngine
): HttpClient {
    return HttpClient(engine) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                }
            )
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    println("PHARMASOFT_HTTP: $message")
                }
            }
            level = LogLevel.HEADERS
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 180000
            connectTimeoutMillis = 30000
            socketTimeoutMillis = 180000
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, "application/json")
        }
    }
}

