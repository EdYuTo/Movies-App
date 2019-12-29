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
        view.updateView(list.toListMap())
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
        fun toMap(): Map<String, String> {
            val map = mutableMapOf<String, String>()
            map.put("id", id.toString())
            map.put("vote_average", vote_average.toString())
            map.put("title", title)
            map.put("poster_url", poster_url)
            map.put("genres", genres.joinToString(", "))
            map.put("release_date", release_date)
            return map
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
        fun toListMap(): List<Map<String, String>> {
            val listMap = mutableListOf<Map<String, String>>()
            movies.forEach {
                listMap.add(it.toMap())
            }
            return listMap
        }
    }
}