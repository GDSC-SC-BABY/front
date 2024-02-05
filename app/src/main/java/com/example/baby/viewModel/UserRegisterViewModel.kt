package com.example.baby.viewModel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baby.R
import com.example.baby.data.User
import com.example.baby.network.Resource
import com.example.baby.network.UserRepository
import com.example.baby.util.SharedPreferenceUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class UserRegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    val nickname = MutableStateFlow("")
    val relationship = MutableStateFlow("")

    val isFormValid: StateFlow<Boolean> = combine(nickname, relationship) { nickname, relationship ->
        nickname.isNotBlank() && relationship.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _userRegistrationState = MutableStateFlow<Resource<User>>(Resource.loading(null))
    val userRegistrationState: StateFlow<Resource<User>> = _userRegistrationState


    fun registerUser(user: User) {
        viewModelScope.launch {
            _userRegistrationState.value = Resource.loading(null)
            try {
                val response = userRepository.registerUser(user)
                if (response.isSuccessful && response.body() != null) {
                    _userRegistrationState.value = Resource.success(response.body())
                } else {
                    _userRegistrationState.value = Resource.error(response.errorBody().toString(), null)
                }
            } catch(e: Exception) {
                _userRegistrationState.value = Resource.error(e.message ?: "An error occurred", null)
            }
        }
    }

    fun setUserInfoToSP(context: Context, nickname: String, relation: String){
        viewModelScope.launch {
            try{
                SharedPreferenceUtil(context).setString("nickname", nickname)
                SharedPreferenceUtil(context).setString("relation", relation)
            } catch (e: Exception){
                Log.d("babyRegister", e.toString())
            }
        }
    }
}
