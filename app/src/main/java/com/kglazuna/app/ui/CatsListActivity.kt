package com.kglazuna.app.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kglazuna.app.ConnectionStateMonitor
import com.kglazuna.app.R
import com.kglazuna.app.ui.adapter.CatListAdapter
import com.kglazuna.app.viewModel.CatsListViewModel
import kotlinx.android.synthetic.main.activity_cats_list.*
import kotlinx.android.synthetic.main.content_cats_list.*
import timber.log.Timber

class CatsListActivity : AppCompatActivity(), CatListAdapter.OnCatClickListener {

    private lateinit var viewModel: CatsListViewModel
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var adapter: CatListAdapter
    var isConnected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cats_list)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this)[CatsListViewModel::class.java]
        layoutManager = GridLayoutManager(this, 2)
        adapter = CatListAdapter(this, emptyList(), this)

        catRecyclerView.layoutManager = layoutManager
        catRecyclerView.adapter = adapter

        viewModel.catList.observe(this, Observer { cats ->
            Timber.d("activity got cat list cats")
            adapter.catList = cats
            adapter.notifyDataSetChanged()
        })

        viewModel.getCats()

        ConnectionStateMonitor.INSTANCE.isConnected.observe(this, Observer {
            when (it) {
                true -> Timber.d("Connected")
                else -> Timber.d("Lost connectivity")
            }
            isConnected = it
        })
    }

    override fun onResume() {
        super.onResume()
        ConnectionStateMonitor.INSTANCE.register(this)
    }

    override fun onPause() {
        super.onPause()
        ConnectionStateMonitor.INSTANCE.unregister(this)
    }

    override fun onCatSelected(position: Int) {
        Timber.d("kitty selected")
        Snackbar.make(catsListLayout, "Kitty selected", Snackbar.LENGTH_SHORT).show()
        val intent = Intent(this, CatRatingActivity::class.java)
        intent.putExtra("position", position)
        startActivity(intent)
    }
}
