package com.practice.recipesapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.recipesapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: PopularAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load all recipes
        val recipes = DatabaseRepository.dao().getAll().filterNotNull()
        adapter = PopularAdapter(ArrayList(recipes), this)

        binding.rvPopular.layoutManager = LinearLayoutManager(this)
        binding.rvPopular.adapter = adapter

        // Open Favorites page
        binding.btnFavorites.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }

        // Category click listeners
        binding.salad.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("CATEGORY_NAME", "Salad")
            startActivity(intent)
        }

        binding.mainDish.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("CATEGORY_NAME", "Main")
            startActivity(intent)
        }

        binding.drinks.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("CATEGORY_NAME", "Drinks")
            startActivity(intent)
        }

        binding.desserts.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("CATEGORY_NAME", "Desserts")
            startActivity(intent)
        }
    }
}
