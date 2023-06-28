package com.yasir.busstracker.ui.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yasir.busstracker.MainActivity
import com.yasir.busstracker.R
import com.yasir.busstracker.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        validationForm()
        binding.RegisterButton.setOnClickListener {
            binding.progressbarReg.visibility = View.VISIBLE
            val validEmailtxt =binding.emailText.text.toString()
            val validPasswordtxt =binding.passET.text.toString()
            val validConformPasstxt =binding.conformPass.text.toString()
            val validRegistrationNo =binding.registrationNo.text.toString()
            if(validEmailtxt.isNotEmpty() && validPasswordtxt.isNotEmpty() && validConformPasstxt.isNotEmpty() && validRegistrationNo.isNotEmpty()){
                if(validPasswordtxt == validConformPasstxt){
                    //TODO:Register user here
                    registerUserFirebase(validEmailtxt,validPasswordtxt)
                }else{
                    validConformPass()
                    binding.progressbarReg.visibility = View.GONE
                    Toast.makeText(requireContext(), "Password not matching", Toast.LENGTH_SHORT).show()
                }

            }else{
                binding.progressbarReg.visibility = View.GONE
                invalidForm()
                resetForm()

                //TODO :Invalid Form
            }
        }

    }
    private fun registerUserFirebase(validEmail:String,validPassword:String){
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(validEmail,validPassword).addOnCompleteListener {
              if(it.isSuccessful){
                  binding.progressbarReg.visibility = View.GONE
                  val intent = Intent(requireContext(),MainActivity::class.java)
                  startActivity(intent)
                  requireActivity().finish()
              }else{
                  binding.progressbarReg.visibility = View.GONE
                  Toast.makeText(requireContext(), "User not valid", Toast.LENGTH_SHORT).show()
              }
        }
    }

    private fun invalidForm() {
        var message =""
        if(binding.NameLayout.helperText !=null){
            message += "\nName: "+binding.NameLayout.helperText
        }
        if(binding.emailLayout.helperText !=null){
            message += "\nEmail: "+binding.emailLayout.helperText
        }
        if(binding.passwordLayout.helperText !=null){
            message += "\nPassword: "+binding.passwordLayout.helperText
        }
        if(binding.conformPasswordLayout.helperText !=null){
            message += "\nConformPassword: "+binding.conformPasswordLayout.helperText
        }
        if(binding.RegistrationLayout.helperText !=null){
            message += "\nRegistration: "+binding.RegistrationLayout.helperText
        }
        AlertDialog.Builder(requireContext())
            .setTitle("Invalid Form")
            .setMessage(message)
            .setPositiveButton("okay"){_,_,->

            }
            .show()

    }
    private fun resetForm(){
        var message = "Name: "+ binding.Name.text
          message +="\nEmail: "+binding.emailText.text
          message += "\nPassword: " +binding.passET.text
          message += "\nConform Password: " +binding.conformPass.text
          message += "\nRegistration No: " +binding.registrationNo.text
    }

    private fun validationForm(){
        emailFocusListner()
        passwordFocusListner()
        conformFocusListner()
        registrationFocusListner()

    }

    private fun registrationFocusListner() {
        binding.registrationNo.setOnFocusChangeListener{ _, focused->
            if(!focused){
                binding.RegistrationLayout.helperText = validRegistration()
            }
        }
    }
    private fun validRegistration(): String? {
        val registrationNo = binding.registrationNo.text.toString()
        if(registrationNo.isEmpty()){
            return "Enter Registration No"
        }
        return null
    }

    private fun conformFocusListner() {
        binding.conformPass.setOnFocusChangeListener{ _, focused->
            if(!focused){
                binding.conformPasswordLayout.helperText = validConformPass()
            }
        }
    }

    private fun validConformPass(): String? {
        val conformPass = binding.conformPass.text.toString()
        if(conformPass!=binding.passET.text.toString()){
            return "Password doesn't Matches"
        }
        return null
    }

    private fun emailFocusListner(){

        binding.emailText.setOnFocusChangeListener{ _, focused->
            if(!focused){
                binding.emailLayout.helperText = validEmail()
            }
        }
    }
    private fun validEmail(): String? {
        val email = binding.emailText.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return "Invalid Email Address"
        }
        return null
    }
    private fun passwordFocusListner(){

        binding.passET.setOnFocusChangeListener{ _, focused->
            if(!focused){
                binding.passwordLayout.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String? {
        val passwordText = binding.passET.text.toString()
        if(passwordText.length < 8 ){
            return "Minimum 8 Character Password"
        }
        if(!passwordText.matches(".*[A-Z].*".toRegex()) ){
            return "Must Contain 1 Upper-case  Character"
        }
        if(!passwordText.matches(".*[a-z].*".toRegex()) ){
            return "Must Contain 1 Lower-case  Character"
        }
        if(!passwordText.matches(".*[@#\$%^&+=].*".toRegex()) ){
            return "Must Contain 1 Special Character (@#\$%^&+=)"
        }
        return null
    }

}


