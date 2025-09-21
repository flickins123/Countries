package com.example.countries.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countries.model.Country
import com.example.countries.network.CountriesService
import kotlinx.coroutines.launch

class CountriesViewModel : ViewModel() {
    private val countriesService = CountriesService()
    
    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> = _countries
    
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    
    init {
        Log.d("CountriesViewModel", "ViewModel initialized")
        loadCountries()
    }
    
    fun loadCountries() {
        viewModelScope.launch {
            Log.d("CountriesViewModel", "Starting to load countries...")
            _loading.value = true
            _error.value = null
            
            countriesService.getCountries()
                .onSuccess { countriesList ->
                    Log.d("CountriesViewModel", "Successfully loaded ${countriesList.size} countries")
                    _countries.value = countriesList
                    _loading.value = false
                }
                .onFailure { exception ->
                    Log.e("CountriesViewModel", "Failed to load countries: ${exception.message}")
                    val errorMessage = when {
                        exception.message?.contains("HTTP") == true -> "Server error: ${exception.message}"
                        exception.message?.contains("timeout") == true -> "Connection timeout. Please check your internet."
                        exception.message?.contains("UnknownHost") == true -> "Cannot reach server. Check your internet connection."
                        else -> "Network error: ${exception.message ?: "Unknown error"}"
                    }
                    _error.value = errorMessage
                    _loading.value = false
                }
        }
    }
    
    fun retry() {
        loadCountries()
    }
}
