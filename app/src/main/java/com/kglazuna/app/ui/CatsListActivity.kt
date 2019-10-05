package com.kglazuna.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.kglazuna.app.R
import com.kglazuna.app.ui.adapter.CatListAdapter
import com.kglazuna.app.viewModel.CatsListViewModel
import kotlinx.android.synthetic.main.activity_cats_list.*
import kotlinx.android.synthetic.main.content_cats_list.*
import timber.log.Timber

class CatsListActivity : AppCompatActivity() {

    private lateinit var viewModel: CatsListViewModel
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var adapter: CatListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cats_list)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this)[CatsListViewModel::class.java]
        layoutManager = GridLayoutManager(this, 2)
        adapter = CatListAdapter(this, emptyList())

        catRecyclerView.layoutManager = layoutManager
        catRecyclerView.adapter = adapter

        viewModel.catList.observe(this, Observer { cats ->
            Timber.d("activity got cat list cats")
            adapter.catList = cats
            adapter.notifyDataSetChanged()
        })

        viewModel.getCats()
    }
}
