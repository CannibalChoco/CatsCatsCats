package com.kglazuna.app.ui

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.kglazuna.app.ConnectionStateMonitor
import com.kglazuna.app.R
import com.kglazuna.app.ui.adapter.CatListAdapter
import com.kglazuna.app.util.showSnackbar
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

            if(cats.isEmpty() && !isConnected) {
                showCouldNotLoadData()
            } else {
                showLoadedOnRetry()
            }
        })

        viewModel.getCats()

        ConnectionStateMonitor.INSTANCE.isConnected.observe(this, Observer {
            when (it) {
                true -> Timber.d("Connected")
                else -> Timber.d("Lost connectivity")
            }

            showConnectivity(it)
            isConnected = it
        })

        catListRetryButton.setOnClickListener{
            if (isConnected) {
                viewModel.getCats()
            } else {
                showSnackbar(catsListLayout,"No network connection")
            }
        }
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
        val intent = Intent(this, CatRatingActivity::class.java)
        intent.putExtra("position", position)
        startActivity(intent)
    }

    fun showConnectivity(isConnected: Boolean) {
        if(isConnected) {
            emptyStateTextCatList.visibility = GONE
        } else {
            emptyStateTextCatList.visibility = VISIBLE
        }
    }

    fun showCouldNotLoadData() {
        emptyStateTextCatList.visibility = VISIBLE
        emptyStateTextCatList.text = getString(R.string.could_not_load_data)
        catListRetryButton.visibility = VISIBLE
    }

    fun showLoadedOnRetry() {
        emptyStateTextCatList.visibility = GONE
        catListRetryButton.visibility = GONE
    }
}
