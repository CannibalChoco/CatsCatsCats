package com.kglazuna.app.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kglazuna.app.model.Cat
import com.kglazuna.app.repository.CatRepo
import timber.log.Timber

class CatRatingViewModel : ViewModel() {

    private var catList: List<Cat> = emptyList()
//    var currentCatPosition: Int = 0
    val selectedCat = MutableLiveData<Cat>()

    init {
        Timber.d("init viewModel")
        catList = CatRepo.catList
    }

    fun selectCat(position: Int){
        selectedCat.value = catList[position]
    }
}
