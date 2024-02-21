package com.example.baby.viewModel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baby.data.BabyFood
import com.example.baby.data.BabyFoodAllResponse
import com.example.baby.data.BabyFoodResponse
import com.example.baby.data.UserDuplicateResponse
import com.example.baby.network.BabyFoodRepository
import com.example.baby.network.Resource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class BabyFoodViewModel(private val babyFoodRepository: BabyFoodRepository) : ViewModel() {

    val amount = MutableStateFlow("")
    val mealTime = MutableStateFlow("")
    val baseMeal = MutableStateFlow("")
    val significant = MutableStateFlow("")

    private val _toppings = mutableStateListOf<String>()
    val toppings: List<String> = _toppings

    private val _toppingAmounts = mutableStateListOf<Int>()
    val toppingAmounts: List<Int> = _toppingAmounts

    private val _selectedImage = MutableLiveData<Uri?>()
    val selectedImage: LiveData<Uri?> = _selectedImage

    fun addTopping() {
        _toppings.add("")
    }

    fun updateTopping(index: Int, topping: String) {
        if (index in _toppings.indices) {
            _toppings[index] = topping
        }
    }

    fun addToppingAmount() {
        _toppingAmounts.add(0)
    }

    fun updateToppingAmount(index: Int, amount: Int) {
        if (index in _toppingAmounts.indices) {
            _toppingAmounts[index] = amount
        }
    }


    fun setMealTime(time: String) {
        mealTime.value = time
    }

    fun setBaseMeal(meal: String) {
        baseMeal.value = meal
    }

    fun setBaseMealAmount(amt: String) {
        amount.value = amt
    }

    fun setSignificant(content: String) {
        significant.value = content
    }

    fun deleteTopping(idx: Int) {
        _toppings.removeAt(idx)
    }

    val isFormValid: StateFlow<Boolean> = combine(amount, mealTime, baseMeal) { amount, mealTime, baseMeal ->
        amount.isNotBlank() && mealTime.isNotBlank() && baseMeal.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)


    private val _babyFoodRegistrationState = MutableStateFlow<Resource<UserDuplicateResponse>>(Resource.loading(null))
    val babyFoodRegistrationState: StateFlow<Resource<UserDuplicateResponse>> = _babyFoodRegistrationState


    private val _babyFoodInfoState = MutableStateFlow<Resource<BabyFoodResponse>>(Resource.loading(null))
    val babyFoodInfoState: StateFlow<Resource<BabyFoodResponse>> = _babyFoodInfoState.asStateFlow()

    private val _allBabyFoodInfoState = MutableStateFlow<Resource<BabyFoodAllResponse>>(Resource.loading(null))
    val allBabyFoodInfoState: StateFlow<Resource<BabyFoodAllResponse>> = _allBabyFoodInfoState.asStateFlow()


    fun registerBabyFood(babyFood: BabyFood) {
        viewModelScope.launch {
            _babyFoodRegistrationState.value = Resource.loading(null)
            try {
                val response = babyFoodRepository.registerBabyFood(babyFood)
                if (response.isSuccessful && response.body() != null) {
                    _babyFoodRegistrationState.value = Resource.success(response.body())
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("API Error", "에러 응답: $errorBody")
                    _babyFoodRegistrationState.value = Resource.error(response.errorBody().toString(), null)
                }
            } catch(e: Exception) {
                Log.e("API Exception", "요청 중 예외 발생: ${e.message}")
                _babyFoodRegistrationState.value = Resource.error(e.message ?: "An error occurred", null)
            }
        }
    }

    fun getBabyFoodInfoByBabyFoodId(bfId: Int) {
        viewModelScope.launch {
            _babyFoodInfoState.value = Resource.loading(null)
            try {
                val response = babyFoodRepository.getBabyFoodInfoByBabyFoodId(bfId)
                if (response.isSuccessful && response.body() != null) {
                    _babyFoodInfoState.value = Resource.success(response.body())
                } else {
                    _babyFoodInfoState.value = Resource.error(response.errorBody()?.string() ?: "Unknown error", null)
                }
            } catch(e: Exception) {
                _babyFoodInfoState.value = Resource.error(e.message ?: "An error occurred", null)
            }
        }
    }

    suspend fun getAllBabyFoodByBabyId(babyId: Int): BabyFoodAllResponse? {
        return try {
            val response = babyFoodRepository.getAllBabyFoodByBabyId(babyId)
            if (response.isSuccessful && response.body() != null) {
                _allBabyFoodInfoState.value = Resource.success(response.body())
                response.body()
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Log.e("API Error", "에러 응답: $errorBody")
                _allBabyFoodInfoState.value = Resource.error(response.errorBody().toString(), null)
                null // 실패하거나 응답 본문이 없는 경우 null 반환
            }
        } catch (e: Exception) {
            Log.e("API Exception", "요청 중 예외 발생: ${e.message}")
            _allBabyFoodInfoState.value = Resource.error(e.message ?: "An error occurred", null)
            null // 예외 발생 시 null 반환
        }
    }

}
