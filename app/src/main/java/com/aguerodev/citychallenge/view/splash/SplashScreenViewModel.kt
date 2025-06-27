package com.aguerodev.citychallenge.view.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aguerodev.citychallenge.domain.entity.City
import com.aguerodev.citychallenge.domain.usecase.DeleteFavoriteCityUseCase
import com.aguerodev.citychallenge.domain.usecase.GetCitiesByNameUseCase
import com.aguerodev.citychallenge.domain.usecase.GetCitiesFromApiUseCase
import com.aguerodev.citychallenge.domain.usecase.GetCitiesFromDbUseCase
import com.aguerodev.citychallenge.domain.usecase.GetFavoriteCitiesUseCase
import com.aguerodev.citychallenge.domain.usecase.SaveFavoriteCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    val getCitiesFromApiUseCase: GetCitiesFromApiUseCase) : ViewModel() {

    private val _cities = MutableStateFlow<List<City>?>(emptyList())
    val cities: StateFlow<List<City>?> = _cities
    private val _isLoading = MutableStateFlow<Boolean>(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getCitiesFromApi(){
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.update { true }
            getCitiesFromApiUseCase.invoke()
            _isLoading.update { false }
        }
    }
}