package com.yasir.busstracker.mainfragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.yasir.busstracker.databinding.FragmentSettingBinding
import com.yasir.busstracker.ui.LoginActivity


class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            activity?.startActivity(Intent(requireContext(),LoginActivity::class.java))
            activity?.finish()

        }
    }


}