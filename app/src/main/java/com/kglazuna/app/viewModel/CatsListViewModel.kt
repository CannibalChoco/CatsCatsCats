package com.kglazuna.app.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kglazuna.app.model.Cat

class CatsListViewModel : ViewModel() {

    var catList = MutableLiveData<List<Cat>>()
        private set

    init {
        // TODO: get cats
    }
}
