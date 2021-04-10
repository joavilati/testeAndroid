package com.jhonata.catapp.util

import okhttp3.Interceptor
import okhttp3.Response

const val baseUrl = "https://www.dnd5eapi.co/api/"

class SupportInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().run {
            addHeader("Content-Type", "application/json")
            addHeader("Accept","application/json")
            build()
        }
        return  chain.proceed(request)
    }
}