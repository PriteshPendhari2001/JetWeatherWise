package com.example.weatherwise.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _isLoading = mutableStateOf(true)
    val isLoading : State<Boolean> = _isLoading


    init {
        viewModelScope.launch {
            delay(2000)
            _isLoading.value = false
        }
    }
}