package com.edyuto.tokenlabmovies.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edyuto.tokenlabmovies.R
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetail() : AppCompatActivity() {
    private var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        id = intent.extras?.getString("id").orEmpty()

        textView.text = "$id"
    }
}
