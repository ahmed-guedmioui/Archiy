package com.core.data.client.http

import com.core.domain.model.file.ArchiyFile
import com.core.domain.util.result.DataError
import com.core.domain.util.result.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.readRawBytes
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.serialization.json.JsonObject

class KtorHttpClient(
    val httpClient: HttpClient
) {

    suspend inline fun <reified Response : Any> get(
        route: String,
        body: Map<String, Any?>? = null,
        bodyJsonObject: JsonObject? = null,
        headers: Map<String, String>? = null
    ): Result<Response, DataError.Remote> {
        return safeCall {
            httpClient.get {
                buildRequest(
                    route, body, bodyJsonObject, headers
                )
            }
        }
    }

    suspend inline fun <reified Response : Any> post(
        route: String,
        body: Map<String, Any?>? = null,
        bodyJsonObject: JsonObject? = null,
        headers: Map<String, String>? = null
    ): Result<Response, DataError.Remote> {
        return safeCall {
            httpClient.post {
                buildRequest(
                    route, body, bodyJsonObject, headers
                )
            }
        }
    }

    suspend inline fun <reified Response : Any> put(
        route: String,
        body: Map<String, Any?>? = null,
        bodyJsonObject: JsonObject? = null,
        headers: Map<String, String>? = null
    ): Result<Response, DataError.Remote> {
        return safeCall {
            httpClient.put {
                buildRequest(
                    route, body, bodyJsonObject, headers
                )
            }
        }
    }

    suspend inline fun <reified Response : Any> patch(
        route: String,
        body: Map<String, Any?>? = null,
        bodyJsonObject: JsonObject? = null,
        headers: Map<String, String>? = null
    ): Result<Response, DataError.Remote> {
        return safeCall {
            httpClient.patch {
                buildRequest(
                    route, body, bodyJsonObject, headers
                )
            }
        }
    }

    suspend inline fun getFileBytes(
        route: String,
        body: Map<String, Any?>? = null,
        bodyJsonObject: JsonObject? = null,
        headers: Map<String, String>? = null
    ): ByteArray {
        val response = httpClient.get {
            buildRequest(
                route, body, bodyJsonObject, headers
            )
        }

        return response.readRawBytes()
    }

    suspend inline fun <reified Response : Any> postFormData(
        route: String,
        file: ArchiyFile,
        body: Map<String, Any?> = mapOf(),
        bodyJsonObject: JsonObject? = null,
        headers: Map<String, String> = mapOf()
    ): Result<Response, DataError.Remote> {
        return safeCall {
            httpClient.post {
                url(constructRoute(route))

                contentType(ContentType.MultiPart.FormData)

                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append(file.name, file.bytes, Headers.build {
                                append(
                                    HttpHeaders.ContentDisposition,
                                    "form-data; name=\"${file.name}\"; filename=\"${file.name}.${file.extension}\""
                                )
                                append(HttpHeaders.ContentType, "application/octet-stream")
                            })

                            setBody(body)

                            bodyJsonObject?.let { setBody(bodyJsonObject) }
                        }
                    )
                )

                headers.forEach { (key, value) ->
                    header(key, value)
                }
            }
        }
    }
}