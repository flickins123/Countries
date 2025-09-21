package com.example.countries

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.countries.adapter.CountriesAdapter
import com.example.countries.viewmodel.CountriesViewModel

class MainActivity : AppCompatActivity() {
    
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTextView: TextView
    private lateinit var viewModel: CountriesViewModel
    private lateinit var adapter: CountriesAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate started")
        setContentView(R.layout.activity_main)
        
        initViews()
        setupViewModel()
        setupRecyclerView()
        setupSwipeRefresh()
        observeViewModel()
        Log.d("MainActivity", "onCreate completed")
    }
    
    private fun initViews() {
        swipeRefresh = findViewById(R.id.swipe_refresh)
        recyclerView = findViewById(R.id.rv_countries)
        progressBar = findViewById(R.id.progress_bar)
        errorTextView = findViewById(R.id.tv_error)
    }
    
    private fun setupViewModel() {
        Log.d("MainActivity", "Setting up ViewModel")
        viewModel = ViewModelProvider(this)[CountriesViewModel::class.java]
        Log.d("MainActivity", "ViewModel created")
    }
    
    private fun setupRecyclerView() {
        adapter = CountriesAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
            setHasFixedSize(true)
        }
    }
    
    private fun setupSwipeRefresh() {
        swipeRefresh.setOnRefreshListener {
            viewModel.retry()
        }
    }
    
    private fun observeViewModel() {
        Log.d("MainActivity", "Setting up observers")
        
        viewModel.countries.observe(this) { countries ->
            Log.d("MainActivity", "Countries received: ${countries.size} items")
            adapter.submitList(countries)
            showContent()
        }
        
        viewModel.loading.observe(this) { isLoading ->
            Log.d("MainActivity", "Loading state: $isLoading")
            swipeRefresh.isRefreshing = isLoading
            if (isLoading && adapter.currentList.isEmpty()) {
                showLoading()
            }
        }
        
        viewModel.error.observe(this) { error ->
            Log.d("MainActivity", "Error received: $error")
            if (error != null && adapter.currentList.isEmpty()) {
                showError()
            }
        }
    }
    
    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        errorTextView.visibility = View.GONE
        swipeRefresh.visibility = View.GONE
    }
    
    private fun showError() {
        progressBar.visibility = View.GONE
        errorTextView.visibility = View.VISIBLE
        swipeRefresh.visibility = View.GONE
    }
    
    private fun showContent() {
        progressBar.visibility = View.GONE
        errorTextView.visibility = View.GONE
        swipeRefresh.visibility = View.VISIBLE
    }
}
