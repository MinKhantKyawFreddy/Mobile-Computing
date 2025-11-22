package com.practice.recipesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.recipesapp.databinding.ActivityFavoritesBinding

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var adapter: PopularAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView
        binding.rvFavorites.layoutManager = LinearLayoutManager(this)
        val favs = DatabaseRepository.dao().getFavorites() // returns List<Recipe>
        adapter = PopularAdapter(ArrayList(favs), this)
        binding.rvFavorites.adapter = adapter

        // Back button listener
        binding.backBtn.setOnClickListener {
            finish() // closes this activity and returns to previous screen
        }
    }
}

