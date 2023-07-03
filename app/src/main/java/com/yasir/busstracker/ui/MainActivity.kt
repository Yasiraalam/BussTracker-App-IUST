package com.yasir.busstracker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.yasir.busstracker.R
import com.yasir.busstracker.databinding.ActivityMainBinding
import com.yasir.busstracker.ui.mainfragments.HomeFragment
import com.yasir.busstracker.ui.mainfragments.MapeFragment
import com.yasir.busstracker.ui.mainfragments.ProfileFragment
import com.yasir.busstracker.ui.mainfragments.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())
        binding.BottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.map -> replaceFragment(MapeFragment())
                R.id.search -> replaceFragment(SearchFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
            }
            return@setOnItemSelectedListener true
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentFL,fragment)
        fragmentTransaction.disallowAddToBackStack()
        fragmentTransaction.commit()

    }
}