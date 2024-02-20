package com.example.baby.viewModel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baby.data.User
import com.example.baby.network.ImageRepository
import com.example.baby.network.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ImageUploadViewModel(application: Application, private val repository: ImageRepository) : AndroidViewModel(application) {

    private val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String> = _imageUrl

    fun uploadImage(imageUri: Uri) {
        viewModelScope.launch {
            try {
                val result = repository.uploadImage(imageUri)
                _imageUrl.value = result
            } catch (e: HttpException) {
                // HttpException 발생 시, 상태 코드와 함께 로그 출력
                Log.e("ImageUploadViewModel", "API Error: HTTP ${e.code()} ${e.message}")
            } catch (e: Exception) {
                // 기타 예외 처리, 상태 코드 없이 오류 로그 출력
                Log.e("ImageUploadViewModel", "Unknown Error: ${e.message}")
            }
        }
    }
}