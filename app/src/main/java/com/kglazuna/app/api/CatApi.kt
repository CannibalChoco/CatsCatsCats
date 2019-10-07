package com.kglazuna.app.api

import com.kglazuna.app.model.Cat
import com.kglazuna.app.model.Vote
import com.kglazuna.app.model.VoteResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CatApi {

    @GET("v1/images/search?")
    suspend fun getCats(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("mime_types") types: String
    ): Response<List<Cat>>

    @POST("v1/votes")
    suspend fun sendVote(
        @Query("api_key") apiKey: String,
        @Body vote: Vote
    ): Response<VoteResponse>

    companion object {
        private const val BASE_URL = "https://api.thecatapi.com"

        fun create(): CatApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(buildClientWithLogger())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(CatApi::class.java)
        }
    }
}
