package com.edyuto.tokenlabmovies.presenter

import com.edyuto.tokenlabmovies.contract.MovieDetailContract
import com.edyuto.tokenlabmovies.model.MovieDetailModel
import com.google.gson.GsonBuilder

class MovieDetailPresenter(_view: MovieDetailContract.View): MovieDetailContract.Presenter {
    private val view = _view
    private val model = MovieDetailModel(this)
    var movieDetails: Detail? = null

    init {
        view.initView()
    }

    override fun requestData(id: String) {
        model.requestData(id)
    }

    override fun responseData(data: String) {
        movieDetails = GsonBuilder().create().fromJson(data, Detail::class.java)
        view.updateView()
    }

    class SpokenLanguage(val iso_639_1: String, val name: String) {
        override fun toString(): String {
            return "$name ($iso_639_1)"
        }
    }

    class ProductionCountry(val iso_3166_1: String, val name: String) {
        override fun toString(): String {
            return "$name ($iso_3166_1)"
        }
    }

    class ProductionCompany(val id: Int, val logo_url: String?, val name: String, val origin_country: String) {
        override fun toString(): String {
            return "$name ($origin_country)"
        }
    }

    class MovieCollection(val id: Int, val name: String, val poster_url: String?, val backdrop_url: String?) {
        override fun toString(): String {
            return "$name"
        }
    }

    class Detail(val adult: Boolean, val backdrop_url: String, val belongs_to_collection: MovieCollection?, val budget: Int, val genres: List<String>,
                         val homepage: String?, val id: Int, val imdb_id: String, val original_language: String, val original_title: String,
                         val overview: String, val popularity: Float, val poster_url: String, val production_companies: List<ProductionCompany>,
                         val production_countries: List<ProductionCountry>, val release_date: String, val revenue: Int, val runtime: Int,
                         val spoken_languages: List<SpokenLanguage>, val status: String, val tagline: String, val title: String,
                         val video: Boolean, val vote_average: Float, val vote_count: Int) {

        override fun toString(): String {
            return "adult: $adult\nbackdrop_url: $backdrop_url\nbelongs_to_collection: $belongs_to_collection\nbudget: $budget\ngenres: ${genres.joinToString(", ")}\n" +
                    "homepage: ${homepage.orEmpty()}\nid: $id\nimdb_id: $imdb_id\noriginal_language: $original_language\noriginal_title: $original_title\n" +
                    "overview: $overview\npopularity: $popularity\nposter_url: $poster_url\nproduction_companies:\n\t${production_companies.joinToString("\n\t")}\n" +
                    "production_countries:\n\t${production_countries.joinToString("\n\t")}\nrelease_date: $release_date\nrevenue: $revenue\nruntime: $runtime\n" +
                    "spoken_languages:\n\t${spoken_languages.joinToString("\n\t")}\nstatus: $status\ntagline: $tagline\ntitle: $title\n" +
                    "video: $video\nvote_average: $vote_average\nvote_count: $vote_count"
        }

        fun getLanguage(lang: String): String {
            for (language in spoken_languages)
                if (lang == language.iso_639_1)
                    return "${language.name} (${language.iso_639_1})"
            return ""
        }
    }
}