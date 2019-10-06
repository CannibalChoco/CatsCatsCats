package com.kglazuna.app.api

import com.kglazuna.app.model.Cat
import com.kglazuna.app.model.Vote
import okhttp3.HttpUrl
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CatApi {

    @GET("/v1/images/search?")
    suspend fun getCats(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int
    ): Response<List<Cat>>

    @POST("v1/votes")
    suspend fun sendVote(
        @Query("api_key") apiKey: String,
        @Body vote: Vote
    )

    companion object {
        private const val BASE_URL = "https://api.thecatapi.com"

        fun create(): CatApi = create(HttpUrl.parse(BASE_URL)!!)

        private fun create(httpUrl: HttpUrl): CatApi {
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(buildClientWithLogger())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(CatApi::class.java)
        }
    }
}
