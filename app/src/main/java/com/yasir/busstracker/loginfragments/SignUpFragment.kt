package com.yasir.busstracker.loginfragments

import android.app.AlertDialog
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yasir.busstracker.ui.MainActivity
import com.yasir.busstracker.R
import com.yasir.busstracker.databinding.FragmentRegisterBinding
import com.yasir.busstracker.models.User

class SignUpFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    lateinit var user: User
    
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var databaseReference: DatabaseReference
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        user = User()
        databaseReference = FirebaseDatabase.getInstance().getReference("User")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        val listOfRoutes = listOf<String>(
            "01--BALSOO KAIMOH WANPHOO BYPASS IUST",
            "02--NATIPORA,CHANIPORA,MATHEN,AHAMEDIYA HOSPITAL,NOWGAM,IUST",
            "03--MOLVI STOP,BUTKADAL,MIL STOP,ZADIBAL,ALAMGARI BAZAR,HAWAL,NOWHATTA,DALGATE ,IUST",
            "04--BUS ADA TRAL,SHAIR ABAD,NOWDAL ,IUST",
            "05--CHADOORA KRALPORA KANIPORA NOWGAM IUST",
            "06--MOOMINABAD ASHAJIPORA JANGLAT MANDI LAL CHOWK KHANABAL IUST",
            "07--NATIPORA, AZADBASTI, BUDSHAHNAGAR, NAIKBAGH, NOWGAM, BYPASS TO IUST",
            "08--YARIPORA, FRISAL, ARWANI, BIJBEHARA, IUST",
            "09--RAMBAGH,SOLINA,TULSIBAGH,JAWAHARNAGER,RAJBAGH,TRC ,IUST",
            "10--RANIWARI,KHANYAR,KHYAM,DALGATE,SONWAR,BATWARA,LASJAN,PAMPORE, IUST",
            "11--KANITAR,UMMER COLONY,LALBAZAR,BUDSHAH MOLHA,MOLVI STOP,MILSTOP,  IUST",
            "12--NAGBAL GULAB BAGH VIA GREEN VALLEY 90FEET P.S. SOURS ALI JAN ROAD SEKIDAFAR KARAN NAGAR FIRE STATION BATAMALOO IUST",
            "13--BUS ADA TRAL,SHAIR ABAD,NOWDAL ,IUST",
            "14--RAZIA KADAL BOHRI KADAL GOUSIA HOSPITAL BARBARSHAH VIA GANDHI COLLEGE MUNWARABAD TRC IUST",
            "15--PULWAMA,PRICHOO,GANGOO,PINGLINA,PAHOO,KAKAPORA,SAMBORA,GALANDER, IUST",
            "16--OMPORA,BUDGAM,PEERBAGH,HYDERPORA,SANATNAGAR,CHANIPORA,BYPASS , IUST",
            "17--HYDERPORA,PARRYPORA,BAGHAT,BARZULA,SANATNAGER,BYPASS, IUST",
            "18--KANITAR,SADRIBAL,KU,NIET,SADAKADAL,RANIWARI,TRC , IUST",
            "19--BEMINA,PARAMPORA,QAMERWARI,BATMALOO,FIRE SERVICE,TRC ,IUST",
            "20--HABBAK,MALBAGH,ELLAHIBAGH,90 FEET,NOWSHARA,HAWAL,NOWHATTA,MUNWAR,TRC ,IUST",
            "21--MAGAM, KANIHAMA, NARBAL, HMT, BEMINA, HYDERPORA BYPASS, IUST",
            "21-A--HARWAN,SHALIMAR,NISHAT,BRAIN,SONWAR, IUST",
            "21-B--KAMARWARI CROSSING, BEMINA CROSSING, IQBALABAD, GRID STATION, BEMINA BYEPASS, IUST",
            "22--RANGRATH,RAWALPORA,SANATNAGER,BYPASS, IUST",
            "23--DIALGAM MOOMINABAD ASHAJIPORA BYPASS NAIBASTI IUST",
            "24--SHOPIAN,SOOFANAMA,SHARIMAL,TOOKRO,KEIGAM,HALL,NAIKES,BUNDZOO,PULWAMA,KOIL, IUST",
            "25--TRAL I BALA AMIRABAD DADASARA CHANDRIGAM NOORPORA IUST",
            "26--AHMADNAGER,90 FEET, SOURA, ALAMGARI BAZAR,NOWHATTA,GOWSIYA HOSPITAL KHYAM, DALGATE, IUST",
            "27--EIDGAH SAFA KADAL KARAN NAGAR BATAMALOO LAL CHOWK IUST",
            "28--AHMED NAGAR, UMER HAIR, BUCHPORA, SOURA, ALIJAN ROAD, NALAMAR ROAD, RAJOURI KADAL, BABA DEMB, TRC, IUST",
            "29--NAGBAL, PANDACH, GULAB BAGH, ZAKURA CROSSING, HABBAK, KU,RAINAWARI, DALGATE, IUST",
            "30--MUJGUND,HMT,BEMINA,HYDERPORA BYPASS ,IUST",
            "31--MATTAN,IQBAL ABAD,SARNEL,LAZIBAL,SADIQ ABAD,  IUST",
            "32--SRIGUFWARA,CHENIWADER, KANELWON, THUJWARA, TAKIBAL, ZIRPARA, PADSHAHBAGH , IUST",
            "33--NARBAL, HMT, BEMINA, HYDERPORA BYEPASS, NOWGAM BYEPASS, IUST",
            "34--NISHAT, BREEN, NEHRU PARK, DALGATE, SONWAR, PANTHA CHOWK, BYPASS, IUST",
            "35--NATIPORA, CHANPORA, BAGHEMETAB, KRALPORA, IUST",
            "36--KULGAM,KAIMO,WANPHOO,HARNAGH, IUST",
            "37--RAJPORA, PULWAMA,TANGPONA,KOIL,LAJURA,MALANGPORA ,IUST",
            "38--LAZIBAL, KHANABAL, BIJBEHARA, IUST",
            "39--MOLVI STOP, KANITAR, KU, NIET, RANIWARI, KHANYAR, DALGATE, PAMPORE, IUST",
            "40--NAGBAL, KHALMULLA, SHUHAMA, SAIDPORA, BATAPORA, ZAKURA CROSSING, HABBAK, MALLA BAGH, ELLAHI BAGH, 90-FEET SOURA, HAWAL, ALAMGARI BAZAR, NOWHATTA, KHYAM, DALGATE, IUST",
            "41--WATHURA(PETROL PUMP)  KRALPORA BAGH-E-MEHTAB CHANAPORA NOWGAM BYPASS IUST",
            "42--HUMHAMA CHOWK, PEERBAGH, HYDERPORA BRIDGE, SANAT NAGAR, NOWGAM, IUST",
            "43--QAZIGUND TO IUST VIA NH-44"
        )
        val adapter = ArrayAdapter(requireContext(), R.layout.item_list, listOfRoutes)
        binding.Routes.setAdapter(adapter)
        binding.Routes.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, position, id ->
                val itemSelected = adapterView.getItemAtPosition(position)
            }

        validationForm()
        binding.RegisterButton.setOnClickListener {
            binding.progressbarReg.visibility = View.VISIBLE
            val userName = binding.Name.text.toString()
            val validEmailtxt = binding.emailText.text.toString()
            val validPasswordtxt = binding.passET.text.toString()
            val validConformPasstxt = binding.conformPass.text.toString()
            val validRegistrationNo = binding.registrationNo.text.toString()
            val gender = binding.Genders.checkedRadioButtonId
            val routeSelected = binding.Routes.text.toString()

            if (userName.isNotEmpty() && validEmailtxt.isNotEmpty() && validPasswordtxt.isNotEmpty() && validConformPasstxt.isNotEmpty() && validRegistrationNo.isNotEmpty() &&
                gender != -1 && routeSelected.isNotEmpty()
            ) {
                if (validPasswordtxt == validConformPasstxt) {
                    //:Registering user here
                    registerUserFirebase(validEmailtxt, validPasswordtxt)
                } else {
                    validConformPass()
                    binding.progressbarReg.visibility = View.GONE
                    Toast.makeText(requireContext(), "Password not matching", Toast.LENGTH_SHORT)
                        .show()
                }

            } else {
                binding.progressbarReg.visibility = View.GONE
                invalidForm()
                resetForm()
                //:Invalid Form
            }
        }

    }

    //2131231311 MALE code
    //2131231310 FEMALE code
    private fun registerUserFirebase(validEmail: String, validPassword: String) {
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(validEmail, validPassword)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    //TODO info getting
                    uploadUserdata()
                    binding.progressbarReg.visibility = View.GONE
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    activity?.startActivity(intent)
                    activity?.finish()
                } else {
                    binding.progressbarReg.visibility = View.GONE
                    Toast.makeText(requireContext(), "User not valid", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun uploadUserdata() {

        user.userName = binding.Name.text.toString()
        user.userEmail = binding.emailText.text.toString()
        user.userRegistrationNo = binding.registrationNo.text.toString()
        user.route = binding.Routes.text.toString()
        Firebase.firestore.collection("User").document(Firebase.auth.currentUser!!.uid).set(user)
            .addOnSuccessListener {
                //save in realtime database
                SaveDataInRealTimeDB()
                Toast.makeText(requireContext(), "user info saved", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Failed SignIn", Toast.LENGTH_SHORT).show()
            }


    }
    private fun SaveDataInRealTimeDB(){
        val uid = firebaseAuth.currentUser?.uid
        val userName = binding.Name.text.toString().trim()
        val userEmail = binding.emailText.text.toString().trim()
        val userRegistrationNo = binding.registrationNo.text.toString().trim()
        val route = binding.Routes.text.toString().trim()
        val userdata = User(userName,userEmail,userRegistrationNo,route)
        if (uid != null){
            databaseReference.child(uid).setValue(userdata).addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(requireContext(), "UPLOADED SUCCESSFULLY", Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(requireContext(), "Failed to update profile", Toast.LENGTH_SHORT).show()
                }

            }

        }
    }

    private fun invalidForm() {
        var message = ""
        if (binding.NameLayout.helperText != null) {
            message += "\nName: " + binding.NameLayout.helperText
        }
        if (binding.emailLayout.helperText != null) {
            message += "\nEmail: " + binding.emailLayout.helperText
        }
        if (binding.passwordLayout.helperText != null) {
            message += "\nPassword: " + binding.passwordLayout.helperText
        }
        if (binding.conformPasswordLayout.helperText != null) {
            message += "\nConformPassword: " + binding.conformPasswordLayout.helperText
        }
        if (binding.RegistrationLayout.helperText != null) {
            message += "\nRegistration: " + binding.RegistrationLayout.helperText
        }
        if (binding.Genders.checkedRadioButtonId == -1) {
            message += "\nSelect Gender  "
        }
        if (binding.Routes.text.isEmpty()) {
            message += "\nSelect Your Route No"
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Invalid Form")
            .setMessage(message)
            .setPositiveButton("okay") { _, _ ->

            }
            .show()

    }

    private fun resetForm() {
        var message = "Name: " + binding.Name.text
        message += "\nEmail: " + binding.emailText.text
        message += "\nPassword: " + binding.passET.text
        message += "\nConform Password: " + binding.conformPass.text
        message += "\nRegistration No: " + binding.registrationNo.text
    }

    private fun validationForm() {
        emailFocusListener()
        passwordFocusListener()
        conformFocusListener()
        registrationFocusListener()

    }

    private fun registrationFocusListener() {
        binding.registrationNo.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.RegistrationLayout.helperText = validRegistration()
            }
        }
    }


    private fun validateRegistrationNumber(registrationNo: String): Boolean {
        val validRegNo = """^iust\d{10}$""".toRegex(RegexOption.IGNORE_CASE)
        return validRegNo.matches(registrationNo)
    }
    private fun validRegistration(): String? {
        val registrationNo = binding.registrationNo.text.toString()
        if(validateRegistrationNumber(registrationNo)){

            if (registrationNo.isEmpty()) {
                return "Enter Registration No"
            }
            return null
        }else{
            return "invalid Registration No"
        }
    }

    private fun conformFocusListener() {
        binding.conformPass.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.conformPasswordLayout.helperText = validConformPass()
            }
        }
    }

    private fun validConformPass(): String? {
        val conformPass = binding.conformPass.text.toString()
        if (conformPass != binding.passET.text.toString()) {
            return "Password doesn't Matches"
        }
        return null
    }

    private fun emailFocusListener() {

        binding.emailText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.emailLayout.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        val email = binding.emailText.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Invalid Email Address"
        }
        return null
    }

    private fun passwordFocusListener() {

        binding.passET.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.passwordLayout.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String? {
        val passwordText = binding.passET.text.toString()
        if (passwordText.length < 8) {
            return "Minimum 8 Character Password"
        }
        if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            return "Must Contain 1 Upper-case  Character"
        }
        if (!passwordText.matches(".*[a-z].*".toRegex())) {
            return "Must Contain 1 Lower-case  Character"
        }
        if (!passwordText.matches(".*[@#\$%^&+=].*".toRegex())) {
            return "Must Contain 1 Special Character (@#\$%^&+=)"
        }
        return null
    }

}


