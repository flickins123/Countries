package com.example.countries.network

import android.util.Log
import com.example.countries.model.Country
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.TimeUnit

class CountriesService {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    
    private val gson = Gson()
    
    suspend fun getCountries(): Result<List<Country>> = withContext(Dispatchers.IO) {
        try {
            Log.d("CountriesService", "Starting network request...")
            
            val request = Request.Builder()
                .url("https://gist.githubusercontent.com/peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json")
                .addHeader("User-Agent", "Countries-Android-App")
                .build()
            
            Log.d("CountriesService", "Making request to: ${request.url}")
            
            val response = client.newCall(request).execute()
            
            Log.d("CountriesService", "Response code: ${response.code}")
            
            if (!response.isSuccessful) {
                val errorMsg = "HTTP ${response.code}: ${response.message}"
                Log.e("CountriesService", errorMsg)
                return@withContext Result.failure(IOException(errorMsg))
            }
            
            val responseBody = response.body?.string()
            Log.d("CountriesService", "Response body length: ${responseBody?.length ?: 0}")
            
            if (responseBody.isNullOrEmpty()) {
                Log.e("CountriesService", "Empty response body")
                return@withContext Result.failure(IOException("Empty response body"))
            }
            
            val listType = object : TypeToken<List<Country>>() {}.type
            val countries = gson.fromJson<List<Country>>(responseBody, listType)
            
            Log.d("CountriesService", "Parsed ${countries?.size ?: 0} countries")
            
            if (countries.isNullOrEmpty()) {
                Log.e("CountriesService", "No countries found in response")
                return@withContext Result.failure(IOException("No countries found in response"))
            }
            
            Log.d("CountriesService", "Successfully loaded countries")
            Result.success(countries)
        } catch (e: Exception) {
            Log.e("CountriesService", "Network error: ${e.message}", e)
            Result.failure(e)
        }
    }
}
