package com.example.baby.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.baby.network.AuthRepository
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _verificationId = MutableLiveData<String>()
    val verificationId: LiveData<String> = _verificationId

    private val _authResult = MutableLiveData<FirebaseUser?>()
    val authResult: LiveData<FirebaseUser?> = _authResult

    private val _isAuthSuccessful = MutableLiveData<Boolean>()
    val isAuthSuccessful: LiveData<Boolean> = _isAuthSuccessful

    fun sendVerificationCode(phoneNumber: String) {
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // Handle error
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                _verificationId.value = verificationId
            }
        }

        repository.sendVerificationCode(phoneNumber, callbacks)
    }

    fun verifyVerificationCode(code: String) {
        verificationId.value?.let {
            repository.verifyVerificationCode(it, code).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authResult.value = task.result?.user
                    _isAuthSuccessful.value = true

                } else {
                    _isAuthSuccessful.value = false
                }
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        repository.firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _authResult.value = task.result?.user
            } else {
                // Handle error
            }
        }
    }
}