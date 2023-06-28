package com.yasir.busstracker.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.yasir.busstracker.MainActivity
import com.yasir.busstracker.R
import com.yasir.busstracker.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth:FirebaseAuth
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
        binding.SignInBtn.setOnClickListener {
            if (validForm()){
                binding.ProgressBarLogin.visibility = View.VISIBLE
                val email = binding.edtEmail.text.toString()
                val password = binding.edtPassword.text.toString()
                logInUserFirebase(email,password)

                //TODO:login here
            }else{
                binding.errorMsg.visibility= View.VISIBLE
                binding.errorMsg.setText(" Empty Fields").toString()
                //TODO: Error show
            }
        }

    }
    private fun logInUserFirebase(email:String, password:String){
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if(it.isSuccessful){
                binding.ProgressBarLogin.visibility = View.GONE
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }else{
                binding.ProgressBarLogin.visibility = View.GONE
                binding.errorMsg.setText("User Not found")
                binding.errorMsg.visibility = View.VISIBLE
                Toast.makeText(requireContext(), "User not valid", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validForm():Boolean {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()
        return email.isNotEmpty() && password.isNotEmpty()
    }


}