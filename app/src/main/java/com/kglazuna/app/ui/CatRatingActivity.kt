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
    private var isConnected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat_rating)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this)[CatRatingViewModel::class.java]
        val catPosition = intent.getIntExtra("position", 0)
        Timber.d("cat position: $catPosition")

        viewPager = catViewPager
        pagerAdapter = CatImagePagerAdapter(this, viewModel.catList)
        viewPager.adapter = pagerAdapter
        viewPager.currentItem = catPosition

        downVote.setOnClickListener {
            tryVote(Vote.Value.VOTE_DOWN.value)
        }

        upVote.setOnClickListener {
            tryVote(Vote.Value.VOTE_UP.value)
        }

        viewModel.onVotePosted.observe(this, Observer {
            displayVotedMessage(it)
        })

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

    private fun tryVote(vote: Int) {
        if (isConnected) {
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
