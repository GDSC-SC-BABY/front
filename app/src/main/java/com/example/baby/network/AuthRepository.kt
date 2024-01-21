package com.example.baby.network

import android.app.Activity
import com.example.baby.MainActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class AuthRepository(private val activity: Activity) {

    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun sendVerificationCode(phoneNumber: String, callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)             // 현재 Activity 사용
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyVerificationCode(verificationId: String, code: String): Task<AuthResult> {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        return firebaseAuth.signInWithCredential(credential)
    }
}