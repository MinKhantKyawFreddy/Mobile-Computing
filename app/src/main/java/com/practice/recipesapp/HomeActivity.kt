package com.practice.recipesapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.recipesapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: PopularAdapter
    private val recipeList = ArrayList<Recipe>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load all recipes
        val recipes = DatabaseRepository.dao().getAll().filterNotNull()
        recipeList.addAll(recipes)
        adapter = PopularAdapter(recipeList, this)

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

        // ✅ Add recipe button listener (must be here inside onCreate)
        binding.btnAddRecipe.setOnClickListener {
            val intent = Intent(this, AddEditRecipeActivity::class.java)
            startActivityForResult(intent, 100)
        }
    }

    // Handle result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            val title = data.getStringExtra("title")!!
            val ingredients = data.getStringExtra("ingredients")!!
            val steps = data.getStringExtra("steps")!!
            val isEdit = data.getBooleanExtra("isEdit", false)
            val position = data.getIntExtra("position", -1)

            if (isEdit && position >= 0) {
                recipeList[position] = Recipe(title, ingredients, steps)
            } else {
                recipeList.add(Recipe(title, ingredients, steps))
            }
            adapter.notifyDataSetChanged()
        }
    }

    // To delete a recipe
    fun deleteRecipe(position: Int) {
        recipeList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }
}