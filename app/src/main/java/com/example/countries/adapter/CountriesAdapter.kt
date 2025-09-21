package com.example.countries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.R
import com.example.countries.model.Country

class CountriesAdapter : ListAdapter<Country, CountriesAdapter.CountryViewHolder>(CountryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val countryInfo: TextView = itemView.findViewById(R.id.tv_country_info)
        private val capital: TextView = itemView.findViewById(R.id.tv_capital)

        fun bind(country: Country) {
            // Format: "name", "region" "code"
            countryInfo.text = "${country.name}, ${country.region} ${country.code}"
            capital.text = country.capital
        }
    }

    private class CountryDiffCallback : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem == newItem
        }
    }
}
