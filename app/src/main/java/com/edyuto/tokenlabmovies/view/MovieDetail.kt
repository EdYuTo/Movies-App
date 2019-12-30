package com.edyuto.tokenlabmovies.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.edyuto.tokenlabmovies.R
import com.edyuto.tokenlabmovies.contract.MovieDetailContract
import com.edyuto.tokenlabmovies.presenter.MovieDetailPresenter
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.detail_displayer.view.*

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
        detailViewer.layoutManager = LinearLayoutManager(this)
        detailViewer.adapter = DetailAdapter(this)
    }

    override fun updateView(list: String) {
        runOnUiThread {
            detailViewer.adapter = DetailAdapter(this, list)
        }
    }

    private class DetailAdapter(_context: Context, _list: String = ""): RecyclerView.Adapter<CustomViewHolder>() {
        private val context = _context
        private val list = _list

        override fun getItemCount(): Int {
            return 1
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            holder.view.detailText.text = list
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            val layoutInflater = LayoutInflater.from(context).inflate(R.layout.detail_displayer, parent, false)
            return CustomViewHolder(layoutInflater)
        }
    }

    private class CustomViewHolder(_view: View): RecyclerView.ViewHolder(_view) {
        val view = _view
    }
}
