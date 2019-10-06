package com.kglazuna.app.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kglazuna.app.model.Cat
import com.kglazuna.app.model.Vote
import com.kglazuna.app.repository.CatRepo
import kotlinx.coroutines.launch
import timber.log.Timber

class CatRatingViewModel : ViewModel() {

    var catList: List<Cat> = emptyList()
        private set
    val onVotePosted = MutableLiveData<Boolean>()

    init {
        Timber.d("init viewModel")
        catList = CatRepo.catList
    }

    fun sendVote(catPosition: Int, value: Int) {
        val cat = catList[catPosition]
        val vote = Vote(cat.id, value)

        viewModelScope.launch {
            Timber.d("sending vote")
            val response = CatRepo.sendVote(vote)
            when (response.code()) {
                200 -> {
                    Timber.d("Vote posted")
                    onVotePosted.value = true
                }
                else -> {
                    Timber.d("Could not submit vote")
                    onVotePosted.value = false
                }
            }
        }
    }
}
