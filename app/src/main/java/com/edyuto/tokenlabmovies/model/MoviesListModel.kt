package com.edyuto.tokenlabmovies.model

import com.edyuto.tokenlabmovies.contract.MoviesListContract
import okhttp3.*
import java.io.IOException

class MoviesListModel(_presenter: MoviesListContract.Presenter): MoviesListContract.Model {
    private val presenter = _presenter
    private val urlRequest = "https://desafio-mobile.nyc3.digitaloceanspaces.com/movies"

    override fun requestData() {
        val request = Request.Builder().url(urlRequest).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                /* Calling the presenter maybe is not the best approach, try something different... */
                presenter.responseData("{'movies':"+response?.body?.string().orEmpty()+"}")
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Error: Server not reachable. $e")
            }
        })
    }

}