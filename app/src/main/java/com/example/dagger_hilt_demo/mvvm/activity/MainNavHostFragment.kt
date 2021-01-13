package com.example.dagger_hilt_demo.mvvm.activity

import android.content.Context
import androidx.navigation.fragment.NavHostFragment
import com.example.dagger_hilt_demo.mvvm.activity.fragment.MainFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainNavHostFragment : NavHostFragment() {

    @Inject
    lateinit var fragmentFactory: MainFragmentFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        childFragmentManager.fragmentFactory = fragmentFactory
    }
}