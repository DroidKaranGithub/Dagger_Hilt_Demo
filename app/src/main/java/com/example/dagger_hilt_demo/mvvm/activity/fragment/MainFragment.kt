package com.example.dagger_hilt_demo.mvvm.activity.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.dagger_hilt_demo.R
import com.example.dagger_hilt_demo.mvvm.model.Blog
import com.example.dagger_hilt_demo.mvvm.viewmodel.MainStateEvent
import com.example.dagger_hilt_demo.mvvm.viewmodel.MainViewModel
import com.example.dagger_hilt_demo.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainFragment
constructor(
    private val someStrings: String
) : Fragment(R.layout.fragment_main) {

    private val mTAG = "MainFragment"
    lateinit var text: TextView
    lateinit var progress_bar: ProgressBar


    @ExperimentalCoroutinesApi
    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(mTAG, "Here is Some String: $someStrings")

        text = view.findViewById(R.id.text)
        progress_bar = view.findViewById(R.id.progress_bar)

        subScribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetBlogEvents)
    }

    private fun subScribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {

                is DataState.Success<List<Blog>> -> {
                    displayProgressBar(false)
                    appendBlogTitles(dataState.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message)

                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }


    private fun displayError(message: String?) {
        if (message != null) {
            text.text = message
        } else {
            text.text = "Unknown error"
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        progress_bar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun appendBlogTitles(blogs: List<Blog>) {
        val sb = StringBuilder()
        for (blog in blogs) {
            sb.append(blog.title + "\n")
        }

        text.text = sb.toString()
    }

}