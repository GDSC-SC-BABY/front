package com.example.baby.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baby.R
import com.example.baby.data.Baby
import com.example.baby.data.BabyResponse
import com.example.baby.data.User
import com.example.baby.network.BabyRepository
import com.example.baby.network.Resource
import com.example.baby.util.SharedPreferenceUtil
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate


class BabyRegisterViewModel(private val babyRepository: BabyRepository) : ViewModel() {


    var babyName = MutableStateFlow("")
    val birth = MutableStateFlow("")
    val gender = MutableStateFlow("남자")
    val height = MutableStateFlow("")
    val weight = MutableStateFlow("")

    val year = MutableStateFlow(LocalDate.now().year)
    val month = MutableStateFlow(LocalDate.now().monthValue)
    val day = MutableStateFlow(LocalDate.now().dayOfMonth)

    val isFormValid: StateFlow<Boolean> = combine(babyName, birth, gender, weight, height) { name, birth, gender, weight, height ->
        name.isNotBlank() && birth.isNotBlank() && gender.isNotBlank()&& weight.isNotBlank()&& height.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _babyRegistrationState = MutableStateFlow<Resource<BabyResponse>>(Resource.loading(null))
    val babyRegistrationState: StateFlow<Resource<BabyResponse>> = _babyRegistrationState

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
                // babyId,
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


    fun registerBaby(baby: Baby) {
        viewModelScope.launch {
            _babyRegistrationState.value = Resource.loading(null)
            try {
                val response = babyRepository.registerBaby(baby)
                if (response.isSuccessful && response.body() != null) {
                    _babyRegistrationState.value = Resource.success(response.body())
                } else {
                    _babyRegistrationState.value = Resource.error(response.errorBody().toString(), null)
                }
            } catch(e: Exception) {
                _babyRegistrationState.value = Resource.error(e.message ?: "An error occurred", null)
            }
        }
    }
}
