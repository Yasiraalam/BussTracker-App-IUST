package com.yasir.busstracker.viewModel



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yasir.busstracker.models.User
import com.yasir.busstracker.utils.USER_NODE

class ProfileViewModel :ViewModel(){
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val userData = MutableLiveData<User?>()
    fun getUserData(): LiveData<User?> {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            firestore.collection(USER_NODE)
                .document(userId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val user = documentSnapshot.toObject(User::class.java)
                    userData.value = user
                }.addOnFailureListener {

                }
        }

        return userData
    }
}