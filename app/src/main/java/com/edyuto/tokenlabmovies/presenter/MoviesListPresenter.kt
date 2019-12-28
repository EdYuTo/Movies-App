package com.edyuto.tokenlabmovies.presenter

import com.edyuto.tokenlabmovies.contract.MoviesListContract
import com.edyuto.tokenlabmovies.model.MoviesListModel
import com.google.gson.GsonBuilder

class MoviesListPresenter(_view: MoviesListContract.View): MoviesListContract.Presenter {
    private val view = _view
    private val model = MoviesListModel(this)

    init {
        view.initView()
    }

    override fun responseData(data: String) {
        val list = GsonBuilder().create().fromJson(data, Movies::class.java)
        println(list)
        view.updateView(list.toListString())
    }

    override fun requestData() {
        model.requestData()
    }

    /* Example of data:
    * {
    *   "id": 19404,
    *   "vote_average": 9.3,
    *   "title": "Dilwale Dulhania Le Jayenge",
    *   "poster_url": "https://image.tmdb.org/t/p/w200/uC6TTUhPpQCmgldGyYveKRAu8JN.jpg",
    *   "genres": [
    *     "Comedy",
    *     "Drama",
    *     "Romance"
    *   ],
    *   "release_date": "1995-10-20"
    * },
    * */

    /* The class arguments must match the json entries, Gson requirements */
    private class Movie(val id: Int, val vote_average: Float, val title: String, val poster_url: String, val genres: List<String>, val release_date: String) {
        override fun toString(): String {
            return "id: $id\nvote_average: $vote_average\ntitle: $title\nposter_url: $poster_url\ngenres: ${genres.joinToString(", ")}\nrelease_date: $release_date"
        }
    }

    private class Movies(val movies: List<Movie>) {
        override fun toString(): String {
            return movies.joinToString("\n")
        }
        fun toListString(): List<String> {
            val list = mutableListOf<String>()
            movies.forEach {
                list.add(it.toString())
            }
            return list
        }
    }
}