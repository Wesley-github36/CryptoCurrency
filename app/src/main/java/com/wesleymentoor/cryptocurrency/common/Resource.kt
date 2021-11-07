package com.wesleymentoor.cryptocurrency.common

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val msg: String?
) {
    companion object {
        fun <T> loading(data: T? = null) = Resource(Status.LOADING, data, null)
        fun <T> success(data: T?) = Resource(Status.SUCCESS, data, null)
        fun <T> error(msg: String) = Resource(Status.ERROR, null, msg)
    }
}

enum class Status {
    LOADING, SUCCESS, ERROR
}