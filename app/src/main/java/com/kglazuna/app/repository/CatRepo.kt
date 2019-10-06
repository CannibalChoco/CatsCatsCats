package com.kglazuna.app.repository

import com.kglazuna.app.BuildConfig
import com.kglazuna.app.api.CatApi
import com.kglazuna.app.model.Cat
import com.kglazuna.app.model.Vote
import com.kglazuna.app.model.VoteResponse
import retrofit2.Response
import timber.log.Timber

object CatRepo {

    private val catApi = CatApi.create()
    var catList: List<Cat> = emptyList()
        private set

    suspend fun getCats() : List<Cat> {
        val apiResponse = catApi.getCats(BuildConfig.CAT_API_KEY,40)
        Timber.d("response: $apiResponse")
        val catList = apiResponse.body()
        return when (catList) {
            is List<Cat> -> {
                Timber.d("got cats: $catList")
                Timber.d("CatRepo: Cat count: ${catList.size}")
                this@CatRepo.catList = catList
                catList
            }
            else -> {
                Timber.d("got something other than cats")
                emptyList()
            }
        }
    }

    suspend fun sendVote(vote: Vote): Response<VoteResponse> {
        return catApi.sendVote(BuildConfig.CAT_API_KEY, vote)
    }
}
