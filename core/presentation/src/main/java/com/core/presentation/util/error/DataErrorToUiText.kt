package com.core.presentation.util.error

import com.core.domain.util.result.DataError
import com.core.presentation.R

fun DataError.toUiText(): UiText {
    val resource = when(this) {
        DataError.Local.DISK_FULL -> R.string.error_disk_full
        DataError.Local.NOT_FOUND -> R.string.error_not_found
        DataError.Local.UNKNOWN -> R.string.error_unknown
        DataError.Remote.BAD_REQUEST -> R.string.error_bad_request
        DataError.Remote.REQUEST_TIMEOUT -> R.string.error_request_timeout
        DataError.Remote.UNAUTHORIZED -> R.string.error_unauthorized
        DataError.Remote.FORBIDDEN -> R.string.error_forbidden
        DataError.Remote.NOT_FOUND -> R.string.error_not_found
        DataError.Remote.CONFLICT -> R.string.error_conflict
        DataError.Remote.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
        DataError.Remote.NO_INTERNET -> R.string.error_no_internet
        DataError.Remote.PAYLOAD_TOO_LARGE -> R.string.error_payload_too_large
        DataError.Remote.SERVER_ERROR -> R.string.error_server
        DataError.Remote.SERVICE_UNAVAILABLE -> R.string.error_service_unavailable
        DataError.Remote.SERIALIZATION -> R.string.error_serialization
        DataError.Remote.UNKNOWN -> R.string.error_unknown
        DataError.Connection.NOT_CONNECTED -> R.string.error_no_internet
    }
    return UiText.Resource(resource)
}