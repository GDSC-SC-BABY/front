package com.example.baby.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baby.R
import com.example.baby.data.Baby
import com.example.baby.data.BabyFoodAllResponse
import com.example.baby.data.BabyInfo
import com.example.baby.data.BabyResponse
import com.example.baby.data.CoParents
import com.example.baby.data.User
import com.example.baby.network.BabyRepository
import com.example.baby.network.Resource
import com.example.baby.util.SharedPreferenceUtil
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.ZoneId


class BabyRegisterViewModel(private val babyRepository: BabyRepository) : ViewModel() {
    var babyName = MutableStateFlow("")
    val birth = MutableStateFlow("")
    val gender = MutableStateFlow("남자")
    val height = MutableStateFlow("")
    val weight = MutableStateFlow("")

    val year = MutableStateFlow(LocalDate.now().year)
    val month = MutableStateFlow(LocalDate.now().monthValue)
    val day = MutableStateFlow(LocalDate.now().dayOfMonth)

    val isFormValid: StateFlow<Boolean> =
        combine(babyName, birth, gender, weight, height) { name, birth, gender, weight, height ->
            name.isNotBlank() && birth.isNotBlank() && gender.isNotBlank() && weight.isNotBlank() && height.isNotBlank()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _babyRegistrationState =
        MutableStateFlow<Resource<BabyResponse>>(Resource.loading(null))
    val babyRegistrationState: StateFlow<Resource<BabyResponse>> = _babyRegistrationState

    private val _coParentRelations = mutableStateListOf<String>()
    val coParentRelations: List<String> = _coParentRelations

    private val _coParentNicknames = mutableStateListOf<String>()
    val coParentNicknames: List<String> = _coParentNicknames

    private val _coParentsGetState = MutableStateFlow<Resource<List<CoParents>>>(Resource.loading(null))
    val coParentsGetState: StateFlow<Resource<List<CoParents>>> = _coParentsGetState.asStateFlow()


    private val _babyInfoGetState = MutableStateFlow<Resource<BabyInfo>>(Resource.loading(null))
    val babyInfoGetState: StateFlow<Resource<BabyInfo>> = _babyInfoGetState.asStateFlow()

    private val _babyName = MutableStateFlow<String?>(null)
    val name: StateFlow<String?> = _babyName.asStateFlow()

    private val _babyCode = MutableStateFlow<String?>(null)
    val babyCode: StateFlow<String?> = _babyCode.asStateFlow()



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

    fun setBabyInfoToSP(
        context: Context,
        name: String,
        birth: String,
        gender: String,
        height: String,
        weight: String,
        userId: String
    ) {
        viewModelScope.launch {
            try {
                val response = babyRepository.checkDuplicateUserId(userId)

                SharedPreferenceUtil(context).setString("babyName", name)
                SharedPreferenceUtil(context).setString("birth", birth)
                SharedPreferenceUtil(context).setString("gender", gender)
                SharedPreferenceUtil(context).setString("height", height)
                SharedPreferenceUtil(context).setString("weight", weight)
                if (response.babyId != null) {
                    SharedPreferenceUtil(context).setString("babyId", response.babyId)
                }

                if (gender == "남자") {
                    SharedPreferenceUtil(context).setInt("genderIcon", R.drawable.man_icon)
                } else {
                    SharedPreferenceUtil(context).setInt("genderIcon", R.drawable.woman_icon)
                }
                var baby = SharedPreferenceUtil(context).getString("babyId", "bb")
                Log.d("babyID", baby!!)
            } catch (e: Exception) {
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
                    _babyRegistrationState.value =
                        Resource.error(response.errorBody().toString(), null)
                }
                Log.d("registerBaby", response.toString())
            } catch (e: Exception) {
                _babyRegistrationState.value =
                    Resource.error(e.message ?: "An error occurred", null)
            }
        }
    }

    suspend fun getCoParentsByBabyId(babyId: Int): List<CoParents>? {
        return try {
            val response = babyRepository.getCoParentsByBabyId(babyId)
            if (response.isSuccessful && response.body() != null) {
                _coParentsGetState.value = Resource.success(response.body())
                response.body()
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Log.e("API Error", "에러 응답: $errorBody")
                _coParentsGetState.value = Resource.error(response.errorBody().toString(), null)
                null // 실패하거나 응답 본문이 없는 경우 null 반환
            }
        } catch (e: Exception) {
            Log.e("API Exception", "요청 중 예외 발생: ${e.message}")
            _coParentsGetState.value = Resource.error(e.message ?: "An error occurred", null)
            null // 예외 발생 시 null 반환
        }
    }

    suspend fun getBabyInfoByBabyId(babyId: Int): BabyInfo? {
        return try {
            val response = babyRepository.getBabyInfoByBabyId(babyId)
            if (response.isSuccessful && response.body() != null) {
                _babyInfoGetState.value = Resource.success(response.body())
                _babyName.value = response.body()?.name
                _babyCode.value = response.body()?.babyCode
                response.body()
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Log.e("API Error", "에러 응답: $errorBody")
                _babyInfoGetState.value = Resource.error(response.errorBody().toString(), null)
                null // 실패하거나 응답 본문이 없는 경우 null 반환
            }
        } catch (e: Exception) {
            Log.e("API Exception", "요청 중 예외 발생: ${e.message}")
            _babyInfoGetState.value = Resource.error(e.message ?: "An error occurred", null)
            null // 예외 발생 시 null 반환
        }
    }

    fun calculateBabyMonth(birthDateTime: LocalDateTime): Int {
        val currentDate = LocalDate.now(ZoneId.systemDefault())
        val birthDate = birthDateTime.toLocalDate()
        val period = Period.between(birthDate, currentDate)
        return period.years * 12 + period.months
    }

}
