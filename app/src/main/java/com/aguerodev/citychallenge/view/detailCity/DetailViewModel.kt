package com.aguerodev.citychallenge.view.detailCity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aguerodev.citychallenge.domain.entity.City
import com.aguerodev.citychallenge.domain.usecase.GetCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    val getCityUseCase: GetCityUseCase) : ViewModel() {

    private val _isLoading = MutableStateFlow<Boolean>(true)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _city = MutableStateFlow<City?>(null)
    val city: StateFlow<City?> = _city

    fun getCity(id: Long) {
        viewModelScope.launch {
            _isLoading.update { true }
            _city.update {
                getCityUseCase.invoke(id)
            }
            _isLoading.update { false }
        }
    }

}