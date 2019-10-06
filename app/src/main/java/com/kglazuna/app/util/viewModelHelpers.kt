package com.kglazuna.app.util

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.kglazuna.app.repository.CatRepo
import com.kglazuna.app.viewModel.CatRatingViewModel
import com.kglazuna.app.viewModel.CatsListViewModel

// https://proandroiddev.com/view-model-creation-in-android-android-architecture-components-kotlin-ce9f6b93a46b
class BaseViewModelFactory<T>(val creator: () -> T) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return creator() as T
    }
}

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(noinline creator: (() -> T)? = null): T {
    return if (creator == null)
        ViewModelProviders.of(this).get(T::class.java)
    else
        ViewModelProviders.of(this, BaseViewModelFactory(creator)).get(T::class.java)
}

fun catsListViewModel(): () -> CatsListViewModel = {
    val repo = CatRepo
    CatsListViewModel(repo)
}

fun catsRatingViewModel(): () -> CatRatingViewModel = {
    val repo = CatRepo
    CatRatingViewModel(repo)
}
