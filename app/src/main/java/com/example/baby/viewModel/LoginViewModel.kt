package com.example.baby.viewModel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.baby.network.AuthRepository
import com.example.baby.network.FirebaseAuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginViewModel(private val repository: FirebaseAuthRepository) : ViewModel() {
/*    private val _navigateToMainScreen = MutableLiveData<Boolean>()
    val navigateToMainScreen: LiveData<Boolean> = _navigateToMainScreen

    private lateinit var googleSignInClient: GoogleSignInClient
//    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val RC_SIGN_IN = 1313

    // 로그인 결과 반환 변수
    private val _loginResult = MutableSharedFlow<Boolean>()
    var loginResult = _loginResult.asSharedFlow()

    fun tryLogin(context: Context) {
        viewModelScope.launch {
            val account = async {
                getLastSignedInAccount(context)
            }
            setLoginResult(account.await() != null)
        }
    }

    // 이전에 로그인 한 계정이 있는지 확인
    private fun getLastSignedInAccount(context: Context) = GoogleSignIn.getLastSignedInAccount(context)

    private fun setLoginResult(isLogin: Boolean) {
        viewModelScope.launch {
            _loginResult.emit(isLogin)
        }
    }*/
var pref: SharedPreferences? = null
    private var _isChecked = MutableLiveData<Boolean>()
    val isChecked: LiveData<Boolean>
        get() = _isChecked

    private var authRepository: FirebaseAuthRepository = repository
    //private var fireStoreRepo: FireStoreRepository = fireStoreRepository
    private val _userLiveData = authRepository.userLiveData

    val userLiveData: LiveData<FirebaseUser>
        get() = _userLiveData

    // set 함수는 이렇게 만드는 게 맞나? 아니면 더 간단한 방법이 있을까?
    fun setCheckedValue(value: Boolean) {
        _isChecked.value = value
        isAutoLogin()
    }

    private fun getAuthRepository(authRepo: FirebaseAuthRepository){
    }

    suspend fun signUp(navController: NavController, email: String, pw: String){
        withContext(Dispatchers.Main) {
            val result = authRepository.signUp(email, pw)
            //fireStoreRepo.createUser(result)
            //유저 값 DB에 저장
        }
    }
    fun signInUser(email: String, pw: String){
        authRepository.signIn(email, pw)
    }

    private fun isAutoLogin(){
        if(isChecked.value == true){
            pref?.edit()?.apply {
                putBoolean("AutoLoginChecked", true)
                apply()
                Log.d("SharedPreference",
                    pref?.getBoolean("AutoLoginChecked", true).toString()
                )
            }
        }
        else{
            pref?.edit()?.apply {
                putBoolean("AutoLoginChecked", false)
                apply()
                Log.d("SharedPreference",
                    pref?.getBoolean("AutoLoginChecked", false).toString()
                )
            }
        }

    }
}