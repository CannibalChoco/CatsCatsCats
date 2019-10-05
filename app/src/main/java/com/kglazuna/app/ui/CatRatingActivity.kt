package com.kglazuna.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.kglazuna.app.R
import com.kglazuna.app.viewModel.CatRatingViewModel
import kotlinx.android.synthetic.main.activity_cat_rating.*
import kotlinx.android.synthetic.main.content_cat_rating.*
import timber.log.Timber

class CatRatingActivity : AppCompatActivity() {

    private lateinit var viewModel: CatRatingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat_rating)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this)[CatRatingViewModel::class.java]
        val catPosition = intent.getIntExtra("position", 0)
        Timber.d("cat position: $catPosition")

        viewModel.selectedCat.observe(this, Observer { cat ->
            Glide.with(this)
                .load(cat.url)
                .placeholder(R.drawable.ic_cat_placeholder)
                .into(catImageView)
        })

        viewModel.selectCat(catPosition)

    }

}
