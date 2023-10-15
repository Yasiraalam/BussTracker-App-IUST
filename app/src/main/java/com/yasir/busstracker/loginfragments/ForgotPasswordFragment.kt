package com.yasir.busstracker.loginfragments


import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.yasir.busstracker.R
import com.yasir.busstracker.databinding.FragmentForgotBinding


class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentForgotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        emailvalidation()
        binding.SendBtn.setOnClickListener {
            val email = binding.forgotEmailText.text.toString()
            binding.ProgressBarForgot.visibility = View.VISIBLE
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {

                if (it.isSuccessful) {
                    binding.ProgressBarForgot.visibility = View.GONE
                    Toast.makeText(requireContext(), "Check your Email to Reset your Password", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)

                } else {
                    binding.ProgressBarForgot.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Check Mail & Reset Your Password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    private fun emailvalidation(){

        binding.forgotEmailText.setOnFocusChangeListener{ _, focused->
            if(!focused){
                binding.forgotEmailLayout.helperText = validEmail()
            }
        }
    }
    private fun validEmail(): String? {
        val email = binding.forgotEmailText.text.toString().trim()
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches() ){
            return "Invalid Email Address"
        }
        if(email.isEmpty() ){
            return "Empty Email Address"
        }
        return null
    }
}