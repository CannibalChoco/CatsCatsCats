package com.kglazuna.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.kglazuna.app.ConnectionStateMonitor
import com.kglazuna.app.R
import com.kglazuna.app.model.Vote
import com.kglazuna.app.ui.adapter.CatImagePagerAdapter
import com.kglazuna.app.util.catsRatingViewModel
import com.kglazuna.app.util.getViewModel
import com.kglazuna.app.util.showSnackbar
import com.kglazuna.app.viewModel.CatRatingViewModel
import kotlinx.android.synthetic.main.activity_cat_rating.*
import kotlinx.android.synthetic.main.activity_cat_rating.toolbar
import kotlinx.android.synthetic.main.content_cat_rating.*
import timber.log.Timber

class CatRatingActivity : AppCompatActivity() {

    private lateinit var viewModel: CatRatingViewModel
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: CatImagePagerAdapter
    private lateinit var connectionStateMonitor: ConnectionStateMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat_rating)
        setSupportActionBar(toolbar)

        viewModel = getViewModel(catsRatingViewModel())
        val catPosition = intent.getIntExtra("position", 0)
        Timber.d("cat position: $catPosition")

        viewPager = catViewPager
        pagerAdapter = CatImagePagerAdapter(this, viewModel.catList)
        viewPager.adapter = pagerAdapter
        viewPager.currentItem = catPosition

        connectionStateMonitor = ConnectionStateMonitor()

        downVote.setOnClickListener {
            tryVote(Vote.Value.VOTE_DOWN.value)
        }

        upVote.setOnClickListener {
            tryVote(Vote.Value.VOTE_UP.value)
        }

        viewModel.onVotePosted.observe(this, Observer {
            displayVotedMessage(it)
        })
    }

    override fun onResume() {
        super.onResume()
        connectionStateMonitor.register(this)
    }

    override fun onPause() {
        super.onPause()
        connectionStateMonitor.unregister(this)
    }

    private fun tryVote(vote: Int) {
        if (connectionStateMonitor.isConnected) {
            viewModel.sendVote(viewPager.currentItem, vote)
        } else {
            showSnackbar(catRatingLayout, getString(R.string.not_connected_cant_send_vote))
        }
    }

    fun displayVotedMessage(votePosted: Boolean) = when(votePosted) {
        true -> showSnackbar(catRatingLayout, getString(R.string.vote_success))
        else -> showSnackbar(catRatingLayout, getString(R.string.vote_error))
    }
}
