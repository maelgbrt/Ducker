package com.example.ducker.data.remote

import io.ktor.client.call.body
import io.ktor.client.request.get

object FoodApi {
    private const val BASE_URL = "http://51.68.91.213"
    private const val PATH = "/gr-3-6/bdd.json"

    private val client = HttpClientFactory.create()

    suspend fun fetchData(): RemoteApiResponse {
        return client.get(urlString = "$BASE_URL$PATH").body()
    }
}

