package com.khemportfolio.fixnaijateam.api


import okhttp3.*

import java.io.IOException

object FixNaijaApi {
    private val BASE_URL = "https://fixnaija.herokuapp.com/api/"
    private val client = OkHttpClient()
    private val mediaType = MediaType.parse("application/json; charset=utf-8")

    @Throws(IOException::class)
    operator fun get(url: String): String {
        val request = Request.Builder().url(BASE_URL + url).build()
        client.newCall(request).execute().use { response -> return response.body()!!.string() }
    }

    @Throws(IOException::class)
    fun post(uri: String, json: String): String {
        val body = RequestBody.create(mediaType, json)
        val request = Request.Builder()
                .url(BASE_URL + uri)
                .post(body)
                .build()

        client.newCall(request).execute().use { response -> return response.body()!!.string() }
    }

}
