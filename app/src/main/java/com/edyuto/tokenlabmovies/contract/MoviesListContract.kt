package com.edyuto.tokenlabmovies.contract

interface MoviesListContract {
    interface Model {
        fun requestData()
    }
    interface View {
        fun initView()
        fun updateView(list: List<Map<String, String>>)
    }
    interface Presenter {
        fun responseData(data: String)
        fun requestData()
    }
}