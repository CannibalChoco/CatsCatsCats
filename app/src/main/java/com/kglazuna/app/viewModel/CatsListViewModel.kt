package com.kglazuna.app.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kglazuna.app.model.Cat
import com.kglazuna.app.repository.CatRepo
import kotlinx.coroutines.launch
import timber.log.Timber

class CatsListViewModel(val catRepo: CatRepo) : ViewModel() {

    var catList = MutableLiveData<List<Cat>>()
        private set

    init {
        Timber.d("init viewModel")
    }

    fun getCats() {
        if (!catList.value.isNullOrEmpty()) return

        viewModelScope.launch {
            catList.value = catRepo.getCats()
            Timber.d("viewModel got cats")
        }
    }
}
