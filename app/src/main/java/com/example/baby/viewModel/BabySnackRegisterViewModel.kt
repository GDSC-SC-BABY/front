package com.example.baby.viewModel

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baby.data.BabyFood
import com.example.baby.data.BabyFoodAllResponse
import com.example.baby.data.BabyFoodResponse
import com.example.baby.data.Snack
import com.example.baby.data.SnackAllResponse
import com.example.baby.data.SnackResponse
import com.example.baby.data.UserDuplicateResponse
import com.example.baby.network.BabySnackRepository
import com.example.baby.network.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class BabySnackRegisterViewModel(private val snackRepository: BabySnackRepository) : ViewModel() {

    val nickname = MutableStateFlow("")
    val snackTime = MutableStateFlow("")
    val amount = MutableStateFlow("")
    val significant = MutableStateFlow("")

    private val _snacks = mutableStateListOf<String>()
    val snacks: List<String> = _snacks

    private val _snackAmounts = mutableStateListOf<Int>()
    val snackAmounts: List<Int> = _snackAmounts

    private val _selectedImage = MutableLiveData<Uri?>()
    val selectedImage: LiveData<Uri?> = _selectedImage

    fun onImagePicked(uri: Uri?) {
        _selectedImage.value = uri
    }

    // 토핑 추가 함수
    fun addSnack() {
        _snacks.add("") // 새로운 토핑(빈 문자열) 추가
    }

    // 특정 인덱스의 토핑을 업데이트하는 함수
    fun updateSnack(index: Int, snack: String) {
        if (index in _snacks.indices) {
            _snacks[index] = snack
        }
    }

    fun addSnackAmounts() {
        _snackAmounts.add(0)
    }

    fun updateSnackAmounts(index: Int, amount: Int) {
        if (index in _snackAmounts.indices) {
            _snackAmounts[index] = amount
        }
    }

    fun setSnackTime(time: String) {
        snackTime.value = time
    }

    fun setSnackAmount(snackAmount: String) {
        amount.value = snackAmount
    }

    fun setSignificant(content: String) {
        significant.value = content
    }

    val isFormValid: StateFlow<Boolean> = combine(nickname, snackTime) { nickname, relationship ->
        nickname.isNotBlank() && relationship.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _snackRegisterState = MutableStateFlow<Resource<UserDuplicateResponse>>(Resource.loading(null))
    val snackRegisterState: StateFlow<Resource<UserDuplicateResponse>> = _snackRegisterState


    private val _snackInfoState = MutableStateFlow<Resource<SnackResponse>>(Resource.loading(null))
    val snackInfoState: StateFlow<Resource<SnackResponse>> = _snackInfoState.asStateFlow()

    private val _allSnackInfoState = MutableStateFlow<Resource<SnackAllResponse>>(Resource.loading(null))
    val allSnackInfoState: StateFlow<Resource<SnackAllResponse>> = _allSnackInfoState.asStateFlow()


    fun registerBabyFood(snack: Snack) {
        viewModelScope.launch {
            _snackRegisterState.value = Resource.loading(null)
            try {
                val response = snackRepository.registerBabySnack(snack)
                if (response.isSuccessful && response.body() != null) {
                    _snackRegisterState.value = Resource.success(response.body())
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("API Error", "에러 응답: $errorBody")
                    _snackRegisterState.value = Resource.error(response.errorBody().toString(), null)
                }
            } catch(e: Exception) {
                Log.e("API Exception", "요청 중 예외 발생: ${e.message}")
                _snackRegisterState.value = Resource.error(e.message ?: "An error occurred", null)
            }
        }
    }

    fun getSnackInfoByBabyFoodId(sId: Int) {
        viewModelScope.launch {
            _snackInfoState.value = Resource.loading(null)
            try {
                val response = snackRepository.getSnackInfoByBabyFoodId(sId)
                if (response.isSuccessful && response.body() != null) {
                    _snackInfoState.value = Resource.success(response.body())
                } else {
                    _snackInfoState.value = Resource.error(response.errorBody()?.string() ?: "Unknown error", null)
                }
            } catch(e: Exception) {
                _snackInfoState.value = Resource.error(e.message ?: "An error occurred", null)
            }
        }
    }

    suspend fun getAllSnackByBabyId(snackId: Int): SnackAllResponse? {
        return try {
            val response = snackRepository.getAllSnackByBabyId(snackId)
            if (response.isSuccessful && response.body() != null) {
                _allSnackInfoState.value = Resource.success(response.body())
                response.body()
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Log.e("API Error", "에러 응답: $errorBody")
                _allSnackInfoState.value = Resource.error(response.errorBody().toString(), null)
                null // 실패하거나 응답 본문이 없는 경우 null 반환
            }
        } catch (e: Exception) {
            Log.e("API Exception", "요청 중 예외 발생: ${e.message}")
            _allSnackInfoState.value = Resource.error(e.message ?: "An error occurred", null)
            null // 예외 발생 시 null 반환
        }
    }
}
