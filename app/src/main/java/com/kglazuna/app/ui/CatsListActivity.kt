package com.kglazuna.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kglazuna.app.R
import kotlinx.android.synthetic.main.activity_cats_list.*

class CatsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cats_list)
        setSupportActionBar(toolbar)
    }
}
