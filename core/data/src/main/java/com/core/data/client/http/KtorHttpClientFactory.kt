package com.core.data.client.http

import com.core.domain.service.session.SessionService
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.api.SendingRequest
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.content.ByteArrayContent
import io.ktor.http.content.TextContent
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


object KtorHttpClientFactory {
    fun build(
        engine: HttpClientEngine,
        sessionService: SessionService
    ): HttpClient {
        return HttpClient(engine) {

            install(HttpTimeout) {
                socketTimeoutMillis = 30_000
                requestTimeoutMillis = 30_000
            }

            install(ContentNegotiation) {
                json(
                    json = Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            install(CurlLoggingPlugin)

            install(createClientPlugin("AuthHeaderPlugin") {
                onRequest { request, _ ->
                    val token = sessionService.getToken()
                    if (!token.isNullOrBlank()) {
                        request.headers.remove(HttpHeaders.Authorization)
                        request.headers.append(HttpHeaders.Authorization, token)
                    }
                }
            })

            defaultRequest {
                contentType(ContentType.Application.Json)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        com.core.domain.util.Logger.i("KtorRequest", message)
                    }
                }
                level = LogLevel.ALL
            }
        }
    }


    private val CurlLoggingPlugin = createClientPlugin("CurlLoggingPlugin") {
        on(SendingRequest) { request, content ->
            val url = request.url.buildString()
            val method = request.method.value

            val curl = buildString {
                append("cURL -X ").append(method)

                request.headers.entries().forEach { (key, values) ->
                    values.forEach { value ->
                        append(" -H \"").append(key).append(": ").append(value).append("\"")
                    }
                }

                append(" \"").append(url).append("\" -L")

                val bodyText: String? = when (content) {
                    is TextContent -> content.text
                    is ByteArrayContent -> content.bytes().decodeToString()
                    is FormDataContent -> null
                    else -> null
                }

                if (!bodyText.isNullOrBlank()) {
                    append(" --data ")
                        .append("'")
                        .append(bodyText.replace("'", "'\"'\"'"))
                        .append("'")
                }
            }
            com.core.domain.util.Logger.d("KtorCurl", curl)
        }
    }
}