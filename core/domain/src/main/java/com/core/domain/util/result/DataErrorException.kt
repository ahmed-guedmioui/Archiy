package com.core.domain.util.result

class DataErrorException(
    val error: DataError
): Exception()