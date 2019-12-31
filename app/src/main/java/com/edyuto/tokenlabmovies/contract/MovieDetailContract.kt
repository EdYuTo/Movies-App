package com.edyuto.tokenlabmovies.contract

interface MovieDetailContract {
    interface Model {
        fun requestData(id: String)
    }
    interface View {
        fun initView()
        fun updateView()
    }
    interface Presenter {
        fun responseData(data: String)
        fun requestData(id: String)
    }
}