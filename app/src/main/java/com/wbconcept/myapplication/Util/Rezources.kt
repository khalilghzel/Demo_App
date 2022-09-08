package com.wbconcept.myapplication.Util

     data class Rezources<out T>(
val status: Status,
val data: T?,
val message: String?
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?): Rezources<T> {
            return Rezources(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(msg: String, data: T? = null): Rezources<T> {
            return Rezources(
                Status.ERROR,
                data,
                msg
            )
        }

        fun <T> loading(data: T? = null): Rezources<T> {
            return Rezources(
                Status.LOADING,
                data,
                null
            )
        }
    }
}