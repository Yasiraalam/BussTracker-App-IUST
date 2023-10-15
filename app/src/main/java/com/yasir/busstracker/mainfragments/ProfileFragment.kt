package com.yasir.busstracker.mainfragments


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.yasir.busstracker.databinding.FragmentProfileBinding
import com.yasir.busstracker.models.User
import com.yasir.busstracker.ui.SettingActivity
import com.yasir.busstracker.utils.USER_NODE


class ProfileFragment : Fragment() {
private lateinit var binding: FragmentProfileBinding
private lateinit var user: User
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater,container,false)
        user = User()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//      binding.settingMenu.setOnClickListener {
//           activity?.startActivity(Intent(requireContext(),SettingActivity::class.java))
//          activity?.finish()
//      }
    }

    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                user = it.toObject<User>()!!
                binding.username.text = user.userName
                binding.Email.text = user.userEmail
                binding.RegistrationNo.text = user.userRegistrationNo
                binding.RouteNo.text = user.route
            }
    }
}