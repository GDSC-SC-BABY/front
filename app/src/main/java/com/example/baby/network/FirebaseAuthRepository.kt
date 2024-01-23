package com.example.baby.network

import android.app.Activity
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.baby.util.SharedPreferenceUtil
import com.google.firebase.auth.*
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository(private val activity: Activity) {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _userLiveData = MutableLiveData<FirebaseUser>()
    val userLiveData: LiveData<FirebaseUser>
        get() = _userLiveData
    init {
        firebaseAuth.addAuthStateListener {
            it.currentUser?.uid?.let {
                SharedPreferenceUtil(activity.baseContext).setString("uid", it)
            }
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    suspend fun signUp(
        email: String,
        pw: String
    ): String? {
        Log.d(ContentValues.TAG, "email: ${email}, pw: ${pw}")

/*            firebaseAuth?.createUserWithEmailAndPassword(email, pw)?.addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
                    Log.d("SignUp autoId", firebaseAuth.currentUser!!.uid)
                } else {
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", it.exception)
                }
            }*/

        return try {
            val auth = FirebaseAuth.getInstance()
            val result = auth.createUserWithEmailAndPassword(email, pw).await()
            result.user?.uid
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            // 이메일 형식이 올바르지 않은 경우
            null
        } catch (e: FirebaseAuthUserCollisionException) {
            // 이미 같은 이메일로 가입된 계정이 있는 경우
            null
        } catch (e: Exception) {
            // 그 외의 예외 처리
            null
        }
    }

    fun signIn(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseAuth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        SharedPreferenceUtil(activity.baseContext).setString("uid", it.result.user?.uid ?: "ERROR")

                    } else {
                    }
                }
        }

//        SharedPreferenceUtil(context).setString("uid", it.result.user?.uid)
    }

    fun googleLogin(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                _userLiveData.postValue(firebaseAuth.currentUser)
            } else {
                // 실패 처리
            }
        }
    }

}
