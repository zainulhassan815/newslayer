package org.dreamerslab.newslayer.core.data.api

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val request = builder.addHeader("X-ACCESS-KEY", apiKey).build()
        return chain.proceed(request)
    }
}
