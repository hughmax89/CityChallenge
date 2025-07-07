package com.aguerodev.citychallenge.view.home


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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getCitiesFromDbUseCase: GetCitiesFromDbUseCase,
    val getCitiesByNameUseCase: GetCitiesByNameUseCase,
    val saveFavoriteCityUseCase: SaveFavoriteCityUseCase,
    val deleteFavoriteCityUseCase: DeleteFavoriteCityUseCase,
    val getFavoriteCitiesUseCase: GetFavoriteCitiesUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow<Boolean>(true)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _cities = MutableStateFlow<List<City>?>(emptyList())
    val cities: StateFlow<List<City>?> = _cities
    private val _citiesFavs = MutableStateFlow<List<City>?>(emptyList())
    val citiesFavs: StateFlow<List<City>?> = _citiesFavs
    private val _loadCitiesFavs = MutableStateFlow<Boolean>(false)
    val loadCitiesFavs: StateFlow<Boolean> = _loadCitiesFavs

    fun getCitiesFromDB() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.update { true }
            _loadCitiesFavs.update { false }
            _cities.update {
                getCitiesFromDbUseCase.invoke()
            }
            _isLoading.update { false }
        }
    }

    fun fetchCityByName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.update { true }
            _loadCitiesFavs.update { false }
            if (name.isNotEmpty()) {
                _cities.update {
                    getCitiesByNameUseCase.invoke(name)
                }
            }
            _isLoading.update { false }
        }
    }

    fun getFavoritesCities() {
        viewModelScope.launch(Dispatchers.IO) {
            _loadCitiesFavs.update { !it }
            _isLoading.update { true }
            _citiesFavs.update {
                getFavoriteCitiesUseCase()
            }
            _isLoading.update {
                false
            }
        }
    }

    fun saveFavoriteCity(_id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.update { true }
            saveFavoriteCityUseCase.invoke(_id)
            _cities.update {
                getCitiesFromDbUseCase.invoke()
            }
            _isLoading.update { false }
        }
    }

    fun deletedFavoriteCity(_id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.update { true }
            deleteFavoriteCityUseCase.invoke(_id)
            _cities.update {
                getCitiesFromDbUseCase.invoke()
            }
            _isLoading.update { false }
        }
    }
}