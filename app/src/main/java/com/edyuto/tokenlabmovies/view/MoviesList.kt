package com.edyuto.tokenlabmovies.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edyuto.tokenlabmovies.R
import kotlinx.android.synthetic.main.activity_movies_list.*
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edyuto.tokenlabmovies.contract.MoviesListContract
import com.edyuto.tokenlabmovies.presenter.MoviesListPresenter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_displayer.view.*

class MoviesList : AppCompatActivity(), MoviesListContract.View {
    private var presenter: MoviesListPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_list)
        presenter = MoviesListPresenter(this)
        presenter?.requestData()
    }

    override fun initView() {
        moviesViewer.layoutManager = LinearLayoutManager(this)
        moviesViewer.adapter = MoviesAdapter(this)
    }

    override fun updateView(list: List<Map<String, String>>) {
        runOnUiThread {
            moviesViewer.adapter = MoviesAdapter(this, list)
        }
    }

    private class MoviesAdapter(_context: Context, _moviesList: List<Map<String, String>> = listOf()): RecyclerView.Adapter<CustomViewHolder>() {
        private val context = _context
        private var moviesList = _moviesList

        override fun getItemCount(): Int {
            return moviesList.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            val layoutInflater = LayoutInflater.from(context)
            val singleLine = layoutInflater.inflate(R.layout.movie_displayer, parent, false)
            return CustomViewHolder(singleLine)
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            Picasso.get().load(moviesList[position].get("poster_url")).into(holder.view.moviePoster)
            holder.view.movieTitle.text = "Title: " + moviesList[position].get("title")
            holder.view.movieGenres.text = "Genres: " + moviesList[position].get("genres")
            holder.view.movieRelease.text = "Release date: " + moviesList[position].get("release_date")
            holder.view.setOnClickListener {
                val intent = Intent(context, MovieDetail::class.java)
                val bundle = Bundle()
                bundle.putString("id", moviesList[position].get("id"))
                intent.putExtras(bundle)
                startActivity(context, intent, null)
            }
        }
    }

    private class CustomViewHolder(_view: View): RecyclerView.ViewHolder(_view) {
        val view = _view
    }
}
