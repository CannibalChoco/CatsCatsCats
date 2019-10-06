package com.kglazuna.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.Snackbar
import com.kglazuna.app.R
import com.kglazuna.app.model.Vote
import com.kglazuna.app.ui.adapter.CatImagePagerAdapter
import com.kglazuna.app.viewModel.CatRatingViewModel
import kotlinx.android.synthetic.main.activity_cat_rating.*
import kotlinx.android.synthetic.main.activity_cat_rating.toolbar
import kotlinx.android.synthetic.main.content_cat_rating.*
import timber.log.Timber

class CatRatingActivity : AppCompatActivity() {

    private lateinit var viewModel: CatRatingViewModel
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: CatImagePagerAdapter

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
            viewModel.sendVote(viewPager.currentItem, Vote.Value.VOTE_DOWN.value)
        }

        upVote.setOnClickListener {
            viewModel.sendVote(viewPager.currentItem, Vote.Value.VOTE_UP.value)
        }

        viewModel.onVotePosted.observe(this, Observer {
            displayVotedMessage(it)
        })

    }

    fun displayVotedMessage(votePosted: Boolean) = when(votePosted) {
        true -> showSnackbar(getString(R.string.vote_success))
        else -> showSnackbar(getString(R.string.vote_error))
    }

    fun showSnackbar(message: String) {
        Snackbar.make(catRatingLayout, message, Snackbar.LENGTH_SHORT).show()
    }
}
