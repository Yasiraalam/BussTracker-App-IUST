package com.yasir.busstracker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yasir.busstracker.R
import com.yasir.busstracker.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}