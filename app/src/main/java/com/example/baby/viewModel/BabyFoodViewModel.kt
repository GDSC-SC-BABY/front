package com.example.baby.viewModel

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baby.data.BabyFood
import com.example.baby.data.BabyFoodResponse
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

    fun setSignificant(significant: String) {
        baseMeal.value = significant
    }

    fun deleteTopping(idx: Int) {
        _toppings.removeAt(idx)
    }

    val isFormValid: StateFlow<Boolean> = combine(amount, mealTime, baseMeal) { amount, mealTime, baseMeal ->
        amount.isNotBlank() && mealTime.isNotBlank() && baseMeal.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)


    private val _babyFoodRegistrationState = MutableStateFlow<Resource<BabyFood>>(Resource.loading(null))
    val babyFoodRegistrationState: StateFlow<Resource<BabyFood>> = _babyFoodRegistrationState


    private val _babyFoodInfoState = MutableStateFlow<Resource<BabyFoodResponse>>(Resource.loading(null))
    val babyFoodInfoState: StateFlow<Resource<BabyFoodResponse>> = _babyFoodInfoState.asStateFlow()


    fun registerBabyFood(babyFood: BabyFood) {
        viewModelScope.launch {
            _babyFoodRegistrationState.value = Resource.loading(null)
            try {
                val response = babyFoodRepository.registerBabyFood(babyFood)
                if (response.isSuccessful && response.body() != null) {
                    _babyFoodRegistrationState.value = Resource.success(response.body())
                } else {
                    _babyFoodRegistrationState.value = Resource.error(response.errorBody().toString(), null)
                }
            } catch(e: Exception) {
                _babyFoodRegistrationState.value = Resource.error(e.message ?: "An error occurred", null)
            }
        }
    }

    fun getBabyFoodInfoByBabyFoodId(bfId: Int) {
        viewModelScope.launch {
            _babyFoodInfoState.value = Resource.loading(null)
            try {
                val response = babyFoodRepository.getUserInfoByBabyFoodId(bfId)
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
}
