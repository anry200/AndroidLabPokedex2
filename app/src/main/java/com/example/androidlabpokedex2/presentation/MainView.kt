package com.example.androidlabpokedex2.presentation

import com.example.androidlabpokedex2.presentation.adapter.DisplayableItem

interface MainView {
    fun showProgress()
    fun showData(items: List<DisplayableItem>)
    fun showError(errorMessage: String)
}

