package com.kglazuna.app.viewModel

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

    //    var currentCatPosition: Int = 0
    var selectedCat: Cat? = null

    init {
        Timber.d("init viewModel")
        catList = CatRepo.catList
    }

    fun sendVote(catPosition: Int, value: Int) {
        val selectedCat = catList[catPosition]
        val cat = catList[catPosition]
        val vote = Vote(cat.id, value)

        viewModelScope.launch {
            Timber.d("sending vote")
            CatRepo.sendVote(vote)
        }
    }
}
