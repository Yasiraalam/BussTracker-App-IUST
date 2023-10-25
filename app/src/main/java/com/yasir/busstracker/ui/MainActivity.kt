package com.yasir.busstracker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.google.firebase.messaging.FirebaseMessaging
import com.yasir.busstracker.R
import com.yasir.busstracker.databinding.ActivityMainBinding
import com.yasir.busstracker.mainfragments.SettingFragment
import com.yasir.busstracker.mainfragments.MapsFragment
import com.yasir.busstracker.mainfragments.ProfileFragment

class MainActivity : AppCompatActivity() {
    private  val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        FirebaseMessaging.getInstance().subscribeToTopic("notifications")
        replaceFragment(MapsFragment())
        binding.BottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.map -> replaceFragment(MapsFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                R.id.setting -> replaceFragment(SettingFragment())
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