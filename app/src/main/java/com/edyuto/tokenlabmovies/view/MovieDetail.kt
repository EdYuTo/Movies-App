package com.edyuto.tokenlabmovies.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.edyuto.tokenlabmovies.R
import com.edyuto.tokenlabmovies.contract.MovieDetailContract
import com.edyuto.tokenlabmovies.presenter.MovieDetailPresenter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.details_displayer.view.*

class MovieDetail: AppCompatActivity(), MovieDetailContract.View {
    private var id = ""
    private var presenter: MovieDetailPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        id = intent.extras?.getString("id").orEmpty()
        presenter = MovieDetailPresenter(this)
        presenter?.requestData(id)
    }

    override fun initView() {
        moviesViewer.layoutManager = LinearLayoutManager(this)
        moviesViewer.adapter = MoviesAdapter(this)
    }

    override fun updateView() {
        runOnUiThread {
            moviesViewer.adapter = MoviesAdapter(this, presenter?.movieDetails)
        }
    }

    private class MoviesAdapter(_context: Context, val movieDetails: MovieDetailPresenter.Detail? = null): RecyclerView.Adapter<CustomViewHolder>() {
        private val context = _context

        override fun getItemCount(): Int {
            return 1
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            if (movieDetails == null) {
                return CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.loading, parent, false))
            }
            val layoutInflater = LayoutInflater.from(context).inflate(R.layout.details_displayer, parent, false)
            return CustomViewHolder(layoutInflater)
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            val view = holder.view
            if (movieDetails is MovieDetailPresenter.Detail) {
                Picasso.get().load(movieDetails.poster_url).into(view.moviePoster)
                view.movieTitle.text = movieDetails.title
                view.tagLine.text = movieDetails.tagline
                view.movieGenres.text = movieDetails.genres.joinToString(", ")
                view.originalTitle.text = movieDetails.original_title
                view.originalLanguage.text = movieDetails.getLanguage(movieDetails.original_language)
                view.movieRelease.text = movieDetails.release_date
                view.runTime.text = movieDetails.runtime.toString()
                view.movieOverview.text = movieDetails.overview
                view.otherLanguages.text = movieDetails.spoken_languages.joinToString("\n")
                if (movieDetails.belongs_to_collection is MovieDetailPresenter.MovieCollection) {
                    view.movieCollection.text = movieDetails.belongs_to_collection.toString()
                    Picasso.get().load(movieDetails.belongs_to_collection.poster_url).into(view.collectionPoster)
                } else {
                    view.movieCollection.text = "No collection"
                }
                view.productionCompanies.text = movieDetails.production_companies.joinToString("\n")
                view.productionCountries.text = movieDetails.production_countries.joinToString("\n")
                view.revenue.text = movieDetails.revenue.toString()
                view.imdb.text = "https://www.imdb.com/title/${movieDetails.imdb_id}"
            }
        }
    }

    private class CustomViewHolder(_view: View): RecyclerView.ViewHolder(_view) {
        val view = _view
    }
}
