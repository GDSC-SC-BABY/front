package com.example.baby.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baby.network.ApiService
import com.example.baby.network.UserRepository
import com.example.baby.util.App
import com.example.baby.util.SharedPreferenceUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
   // private val dbAccessModule = DBAccessModule()
    private lateinit var googleSignInClient : GoogleSignInClient

    // 로그인 결과 반환 변수
    private val _loginResult = MutableSharedFlow<Boolean>()
    var loginResult = _loginResult.asSharedFlow()

    suspend fun hasData(user: FirebaseUser): Boolean {
        var hasId = false

        viewModelScope.launch {
            val userResponse = "abc"
//            val userResponse = userRepository.checkUserId(user.uid).body()
            // composable, activity 간의 이동 고민(베이비 등록 페이지 후 메인 페이지)
            if (userResponse != null) {
                SharedPreferenceUtil(App.context()).setString("uid", user.uid)
                hasId = true
            }
            Log.d("hasId", hasId.toString())
        }.join()

        return hasId
    }

    /*fun hasInfo(context: Context): Boolean {
        val pref = SharedPreferenceUtil(context)
        val savedNickname = pref.getString("nickname", "")
        val savedBabyname = pref.getString("babyName", "")

        return !(savedNickname.isNullOrEmpty() || savedBabyname.isNullOrEmpty())
    }*/
    // 아이디와 기본 정보를 따로 생각하지 말고 기본 정보를 등록해야 아이디가 디비에 등록되도록 하는 건?



    fun tryLogin(context: Context) {
        Log.d("로그인중", "로그인중")
        viewModelScope.launch {
            val account = async {
                getLastSignedInAccount(context)
            }
            delay(2500)
            // 계정 확인 -> true, 없음 -> false 반환
            setLoginResult(account.await() != null)
        }
    }

    // 이전에 로그인 한 계정이 있는지 확인
    private fun getLastSignedInAccount(context: Context) =
        GoogleSignIn.getLastSignedInAccount(context)

    fun setLoginResult(isLogin: Boolean) {
        viewModelScope.launch {
            _loginResult.emit(isLogin)
        }
    }


    private val _event = MutableSharedFlow<LoginEvent>()
    val event = _event.asSharedFlow()


    val time = System.currentTimeMillis()
    private val timeStamp: String = SimpleDateFormat("yyyy-MM-dd HH:MM:ss", Locale.KOREA).format(
        Date(time)
    )

    fun toMainActivity() = viewModelScope.launch { _event.emit(LoginEvent.ToMain) }

    sealed class LoginEvent{
        object ToMain : LoginEvent()
    }

/*    suspend fun checkNickname() {
        if (userNicknameInput.isEmpty()) {
            isEmpty.value = true
            availableNickname.value = false
        }else{
            isEmpty.value = false
            val result = dbAccessModule.checkNickname(userNicknameInput)
            availableNickname.value = if (!result) {
                isOverlap.value = true
                true
            }else{
                isOverlap.value = false
                false
            }}
    }*/

}