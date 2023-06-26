package com.yasir.busstracker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yasir.busstracker.R
import com.yasir.busstracker.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

}