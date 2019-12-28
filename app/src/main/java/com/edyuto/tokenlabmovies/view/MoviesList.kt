package com.edyuto.tokenlabmovies.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edyuto.tokenlabmovies.R
import kotlinx.android.synthetic.main.activity_movies_list.*
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.edyuto.tokenlabmovies.contract.MoviesListContract
import com.edyuto.tokenlabmovies.presenter.MoviesListPresenter

class MoviesList : AppCompatActivity(), MoviesListContract.View {
    private var presenter: MoviesListPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_list)
        presenter = MoviesListPresenter(this)
        presenter?.requestData()
    }

    override fun initView() {
        moviesViewer.adapter = MoviesAdapter(this)
    }

    override fun updateView(list: List<String>) {
        runOnUiThread {
            moviesViewer.adapter = MoviesAdapter(this, list)
        }
    }

    private class MoviesAdapter(_context: Context, _moviesList: List<String> = listOf()): BaseAdapter() {
        private val context = _context
        private var moviesList = _moviesList

        override fun getCount(): Int {
            return moviesList.size
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val textView = TextView(context)
            textView.text = moviesList[position]
            return textView
        }
    }
}
/*button.setOnClickListener {
    intent = Intent(this, MovieDetail::class.java)
    startActivity(intent)
}*/
