package com.tyyod.jdmallaccessibility.ui.activity

import android.os.Bundle
import android.os.Process
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.orhanobut.logger.Logger
import com.tyyod.jdmallaccessibility.R
import com.tyyod.jdmallaccessibility.databinding.ActivityMainBinding
import com.tyyod.jdmallaccessibility.ui.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainViewModel by viewModels<MainViewModel>()
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityMainBinding.mainViewModel = mainViewModel
    }
}