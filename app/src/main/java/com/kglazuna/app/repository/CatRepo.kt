package com.kglazuna.app.repository

import com.kglazuna.app.BuildConfig
import com.kglazuna.app.api.CatApi
import com.kglazuna.app.model.Cat
import com.kglazuna.app.model.Vote
import timber.log.Timber

object CatRepo {

    private val catApi = CatApi.create()
    var catList: List<Cat> = emptyList()
        private set

    suspend fun getCats() : List<Cat> {
        return try {
            val apiResponse = catApi.getCats(BuildConfig.CAT_API_KEY,40, "jpg,png")
            Timber.d("response: $apiResponse")
            val catList = apiResponse.body()
            when (catList) {
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
        } catch (t: Throwable) {
            Timber.d(t)
            emptyList()
        }
    }

    suspend fun sendVote(vote: Vote): Boolean {
        return try {
            val response = catApi.sendVote(BuildConfig.CAT_API_KEY, vote)
            when (response.code()) {
                200 -> true
                else -> false
            }
        } catch (t: Throwable) {
            Timber.d(t)
            false
        }
    }
}
