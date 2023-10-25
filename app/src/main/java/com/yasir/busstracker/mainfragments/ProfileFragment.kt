package com.yasir.busstracker.mainfragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.yasir.busstracker.databinding.FragmentProfileBinding
import com.yasir.busstracker.models.User
import com.yasir.busstracker.utils.USER_NODE
import com.yasir.busstracker.viewModel.ProfileViewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var user: User
    private lateinit var viewModel: ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        user = User()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        viewModel.getUserData().observe(viewLifecycleOwner) { user ->
            binding.username.text = user?.userName
            binding.Email.text = user?.userEmail
            binding.RegistrationNo.text = user?.userRegistrationNo
            binding.RouteNo.text = user?.route
            Log.d("Yasir", "data:${user?.userName } ")
            Log.d("Yasir", "data:${user?.route } ")
            Log.d("Yasir", "data:${user?.userEmail } ")
        }
        viewModel.getUserData()
    }

//    override fun onStart() {
//        super.onStart()
//        val uid  = firebase.auth.currentUser!!.uid
//        firebase.firestore.collection(USER_NODE).document(uid).get()
//            .addOnSuccessListener {
//                user = it.toObject<User>()!!
//                binding.username.text = user.userName
//                binding.Email.text = user.userEmail
//                binding.RegistrationNo.text = user.userRegistrationNo
//                binding.RouteNo.text = user.route
//            }
//    }
}