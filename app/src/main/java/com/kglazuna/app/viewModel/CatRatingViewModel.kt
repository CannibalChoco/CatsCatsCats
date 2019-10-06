package com.kglazuna.app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kglazuna.app.model.Cat
import com.kglazuna.app.model.Vote
import com.kglazuna.app.repository.CatRepo
import com.kglazuna.app.util.SingleLiveEvent
import kotlinx.coroutines.launch
import timber.log.Timber

class CatRatingViewModel(val catRepo: CatRepo) : ViewModel() {

    var catList: List<Cat> = emptyList()
        private set
    val onVotePosted = SingleLiveEvent<Boolean>()

    init {
        Timber.d("init viewModel")
        catList = catRepo.catList
    }

    fun sendVote(vote: Vote) {
        viewModelScope.launch {
            Timber.d("sending vote")
            when (catRepo.sendVote(vote)) {
                true -> {
                    Timber.d("Vote posted")
                    onVotePosted.value = true
                }
                false -> {
                    Timber.d("Could not submit vote")
                    onVotePosted.value = false
                }
            }
        }
    }

    fun createVote(catPosition: Int, value: Int): Vote {
        val cat = catList[catPosition]
        return Vote(cat.id, value)
    }
}
