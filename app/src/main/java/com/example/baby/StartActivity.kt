package com.example.baby

import LoginScreen
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.baby.data.NavigationRoutes
import com.example.baby.network.UserRepository
import com.example.baby.screen.BabyRegisterScreen
import com.example.baby.screen.GuideScreen
import com.example.baby.screen.UserRegisterScreen
import com.example.baby.util.App
import com.example.baby.util.SharedPreferenceUtil
import com.example.baby.viewModel.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StartActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 1313
    private val viewModel by viewModels<LoginViewModel>()
    private val userRegisterViewModel: UserRegisterViewModel by viewModels {
        UserViewModelFactory(UserRepository())
    }
    private val babyRegisterViewModel by viewModels<BabyRegisterViewModel> {
        BabyRegisterViewModelFactory(UserRepository())
    }
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.teddy_bear),
                    contentDescription = "",
                    modifier = Modifier.padding(20.dp)
                )
//                Text(text = "로그인 확인중", fontSize = 30.sp, modifier = Modifier.background(color = colorResource(
//                    id = R.color.backGreen
//                )))
            }
        }
        // 로그인 시도
        viewModel.tryLogin(this)

        lifecycleScope.launchWhenCreated {
            launch {
                viewModel.loginResult.collect { isLogin ->
                    if (isLogin) {
                        Log.d("로그인 되어있음", auth.currentUser.toString())
                        if (auth.currentUser != null) {
                            Log.d("token", auth.currentUser!!.getIdToken(true).toString())
                            val hasId = viewModel.hasId(auth.currentUser!!)
                            Log.d("아이디 있는지", hasId.toString())
                            Log.d("uid", auth.currentUser!!.uid.toString())

                            if (hasId) {
                                Log.d("아이디랑 기본 정보 모두 저장됨", "메인으로")
                                toMainActivity()
                            } else {
                                Log.d("아이디가 디비에 저장 안 됨(유저 정보 등록 안함)", "등록으로")
                                setContent {
                                    val navController = rememberNavController()

                                    // NavHost 로 네비게이션 결정
                                    NavHost(navController, "registerScreen")
                                    {
                                        composable(NavigationRoutes.RegisterScreen.route) {
                                            UserRegisterScreen(
                                                viewModel = userRegisterViewModel,
                                                navController = navController
                                            )
                                        }
                                        composable(NavigationRoutes.BabyRegisterScreen.route) {
                                            BabyRegisterScreen(
                                                viewModel = babyRegisterViewModel,
                                                navController = navController
                                            )
                                        }
                                    }
                            }
                        }
                    } else {
                        Log.d("로그인 되어있음", "로그인 되어있지만? 유저가 없음")
                    }
                } else {
                Log.d("로그인 안되어있음", "로그인 안되어있음")
                // 로그인 안되어있을 때 로그인 페이지 열림
                setContent {
                    LoginScreen {
                        googleLogin()
                    }
                }
            }
            }
        }
        launch {
            viewModel.event.collect { event ->
                when (event) {
                    LoginViewModel.LoginEvent.ToMain -> toMainActivity()
                }
            }
        }

    }

}

// 로그인 객체 생성
private fun googleLogin() {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        // 빨간줄이지만 토큰 문제라 실행 가능
        .requestIdToken("292129982271-dncmaevefv0n74od6pom19v5m37he4no.apps.googleusercontent.com")
        .requestEmail()
        .build()

    googleSignInClient = GoogleSignIn.getClient(this, gso)

    googleSignIn()
}

// 구글 회원가입
private fun googleSignIn() {
    val signInIntent = googleSignInClient.signInIntent
    startActivityForResult(signInIntent, RC_SIGN_IN)
}

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == RC_SIGN_IN) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account)
        } catch (e: ApiException) {
            Toast.makeText(this, "구글 회원가입에 실패하였습니다: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    } else {
        /*no-op*/
    }
}

// account 객체에서 id 토큰 가져온 후 Firebase 인증
private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
    val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
    auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
        Log.d("로그인중", "로그인 되어야함1")
        if (task.isSuccessful) {
            Log.d("로그인중", "로그인 되어야함2")
            auth.currentUser?.let {
                Log.d("로그인중", "로그인 되어야함3")
                CoroutineScope(Dispatchers.Main).launch {
                    viewModel.setLoginResult(true)
                }
            }
        }
    }
}


private fun toMainActivity() {
    startActivity(Intent(this, MainActivity::class.java))
}
}