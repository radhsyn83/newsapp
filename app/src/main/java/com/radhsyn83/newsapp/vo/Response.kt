package com.radhsyn83.newsapp.vo
import com.radhsyn83.newsapp.vo.Status.ERROR
import com.radhsyn83.newsapp.vo.Status.LOADING
import com.radhsyn83.newsapp.vo.Status.SUCCESS

class Response<T>(val status: Status, val body: T, val message: String?) {
    companion object {
        fun <T> success(body: T): Response<T> = Response(SUCCESS, body, null)

        fun <T> loading(body: T): Response<T> = Response(LOADING, body, null)

        fun <T> error(body: T, msg: String): Response<T> = Response(ERROR, body, msg)
    }
}