package com.core.domain.model.file

data class ArchiyFile(
    val name: String,
    val bytes: ByteArray,
    val extension: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ArchiyFile

        if (name != other.name) return false
        if (!bytes.contentEquals(other.bytes)) return false
        if (extension != other.extension) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + bytes.contentHashCode()
        result = 31 * result + extension.hashCode()
        return result
    }
}
