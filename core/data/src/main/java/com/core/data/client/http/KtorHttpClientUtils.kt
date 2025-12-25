package com.core.data.client.http

import com.core.data.BuildConfig
import com.core.domain.util.result.DataError
import com.core.domain.util.result.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonObject
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.nio.channels.UnresolvedAddressException


fun HttpRequestBuilder.buildRequest(
    route: String,
    body: Map<String, Any?>? = null,
    bodyJsonObject: JsonObject? = null,
    headers: Map<String, String>? = null
) {
    url(constructRoute(route))

    body?.let { setBody(body) }

    bodyJsonObject?.let { setBody(bodyJsonObject) }

    headers?.forEach { (key, value) ->
        header(key, value)
    }
}


suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, DataError.Remote> {
    return try {
        val response = execute()
        return responseToResult(response)
    } catch (e: UnknownHostException) {
        e.printStackTrace()
        Result.Error(DataError.Remote.NO_INTERNET)
    } catch (e: UnresolvedAddressException) {
        e.printStackTrace()
        Result.Error(DataError.Remote.NO_INTERNET)
    } catch (e: ConnectException) {
        e.printStackTrace()
        Result.Error(DataError.Remote.NO_INTERNET)
    } catch (e: SocketTimeoutException) {
        e.printStackTrace()
        Result.Error(DataError.Remote.REQUEST_TIMEOUT)
    } catch (e: HttpRequestTimeoutException) {
        e.printStackTrace()
        Result.Error(DataError.Remote.REQUEST_TIMEOUT)
    } catch (e: SerializationException) {
        e.printStackTrace()
        Result.Error(DataError.Remote.SERIALIZATION)
    } catch (e: Exception) {
        e.printStackTrace()
        currentCoroutineContext().ensureActive()
        Result.Error(DataError.Remote.UNKNOWN)
    }
}

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, DataError.Remote> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                e.printStackTrace()
                Result.Error(DataError.Remote.SERIALIZATION)
            }
        }

        400 -> Result.Error(DataError.Remote.BAD_REQUEST)
        401 -> Result.Error(DataError.Remote.UNAUTHORIZED)
        403 -> Result.Error(DataError.Remote.FORBIDDEN)
        404 -> Result.Error(DataError.Remote.NOT_FOUND)
        408 -> Result.Error(DataError.Remote.REQUEST_TIMEOUT)
        409 -> Result.Error(DataError.Remote.CONFLICT)
        413 -> Result.Error(DataError.Remote.PAYLOAD_TOO_LARGE)
        429 -> Result.Error(DataError.Remote.TOO_MANY_REQUESTS)
        500 -> Result.Error(DataError.Remote.SERVER_ERROR)
        503 -> Result.Error(DataError.Remote.SERVICE_UNAVAILABLE)
        else -> Result.Error(DataError.Remote.UNKNOWN)
    }
}

fun constructRoute(route: String): String {
    return when {
        route.contains(BuildConfig.BASE_URL) -> route
        route.startsWith("/") -> "${BuildConfig.BASE_URL}$route"
        else -> "${BuildConfig.BASE_URL}/$route"
    }
}