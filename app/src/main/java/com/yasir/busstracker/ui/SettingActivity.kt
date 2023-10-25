package com.yasir.busstracker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yasir.busstracker.databinding.ActivitySettingBinding
import com.yasir.busstracker.viewModel.ProfileViewModel

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var viewModel:ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}