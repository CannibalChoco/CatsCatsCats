package com.kglazuna.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kglazuna.app.R
import com.kglazuna.app.viewModel.CatsListViewModel
import kotlinx.android.synthetic.main.activity_cats_list.*

class CatsListActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this)[CatsListViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cats_list)
        setSupportActionBar(toolbar)

        viewModel.catList.observe(this, Observer { cats ->
            // TODO: pass cats to recyclerview

        })
    }
}
