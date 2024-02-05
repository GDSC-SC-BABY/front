package com.example.baby.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baby.R
import com.example.baby.data.User
import com.example.baby.network.Resource
import com.example.baby.network.UserRepository
import com.example.baby.util.SharedPreferenceUtil
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class BabyRegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    val babyName = MutableStateFlow("")
    val birth = MutableStateFlow("")
    val gender = MutableStateFlow("남자")
    val height = MutableStateFlow("")
    val weight = MutableStateFlow("")

    val isFormValid: StateFlow<Boolean> = combine(babyName, birth, gender, weight, height) { name, birth, gender, weight, height ->
        name.isNotBlank() && birth.isNotBlank() && gender.isNotBlank()&& weight.isNotBlank()&& height.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _userRegistrationState = MutableStateFlow<Resource<User>>(Resource.loading(null))
    val userRegistrationState: StateFlow<Resource<User>> = _userRegistrationState

    private val _coParentRelations = mutableStateListOf<String>()
    val coParentRelations: List<String> = _coParentRelations

    private val _coParentNicknames = mutableStateListOf<String>()
    val coParentNicknames: List<String> = _coParentNicknames


    fun addCoParentRelation(relation: String) {
        _coParentRelations.add(relation)
    }

    fun deleteCoParentRelation(idx: Int) {
        if (idx >= 0 && idx < _coParentRelations.size) {
            _coParentRelations.removeLast()
        }
    }

    fun deleteAllCoParentRelation() {
        _coParentRelations.clear()
    }

    fun addCoParentNickname(nickname: String) {
        _coParentNicknames.add(nickname)
    }

    fun deleteCoParentNickname(idx: Int) {
        _coParentNicknames.removeAt(idx)
    }

    fun setBabyInfoToSP(context: Context, name: String, birth: String, gender: String, height: String, weight: String){
        viewModelScope.launch {
            try{
                SharedPreferenceUtil(context).setString("babyName", name)
                SharedPreferenceUtil(context).setString("birth", birth)
                SharedPreferenceUtil(context).setString("gender", gender)
                SharedPreferenceUtil(context).setString("height", height)
                SharedPreferenceUtil(context).setString("weight", weight)
                if(gender == "남자"){
                    SharedPreferenceUtil(context).setInt("genderIcon", R.drawable.man_icon)
                } else{
                    SharedPreferenceUtil(context).setInt("genderIcon", R.drawable.woman_icon)
                }
            } catch (e: Exception){
                Log.d("babyRegister", e.toString())
            }
        }
    }


//    fun registerUser(baby: Baby) {
//        viewModelScope.launch {
//            _userRegistrationState.value = Resource.loading(null)
//            try {
//                val response = userRepository.registerUser(baby)
//                if (response.isSuccessful && response.body() != null) {
//                    _userRegistrationState.value = Resource.success(response.body())
//                } else {
//                    _userRegistrationState.value = Resource.error(response.errorBody().toString(), null)
//                }
//            } catch(e: Exception) {
//                _userRegistrationState.value = Resource.error(e.message ?: "An error occurred", null)
//            }
//        }
//    }
}
