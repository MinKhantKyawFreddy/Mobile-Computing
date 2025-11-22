package com.practice.recipesapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.recipesapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private var filteredList = ArrayList<Recipe>()
    private lateinit var adapter: PopularAdapter
    private val recipeList = ArrayList<Recipe>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load all recipes
        val recipes = DatabaseRepository.dao().getAll().filterNotNull()
        recipeList.addAll(recipes)
        filteredList.addAll(recipes)

        adapter = PopularAdapter(filteredList, this)

        binding.rvPopular.layoutManager = LinearLayoutManager(this)
        binding.rvPopular.adapter = adapter

        // SEARCH
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterRecipes(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterRecipes(newText)
                return true
            }
        })

        // FILTERS
        binding.btnVegan.setOnClickListener { filterByDiet("Vegan") }
        binding.btnVegetarian.setOnClickListener { filterByDiet("Vegetarian") }
        binding.btnGlutenFree.setOnClickListener { filterByDiet("Gluten-Free") }

        // OPEN FAVORITES
        binding.btnFavorites.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }

        // CATEGORIES
        binding.salad.setOnClickListener {
            openCategory("Salad")
        }
        binding.mainDish.setOnClickListener {
            openCategory("Main")
        }
        binding.drinks.setOnClickListener {
            openCategory("Drinks")
        }
        binding.desserts.setOnClickListener {
            openCategory("Desserts")
        }

        // ADD RECIPE
        binding.btnAddRecipe.setOnClickListener {
            val intent = Intent(this, AddEditRecipeActivity::class.java)
            startActivityForResult(intent, 100)
        }
    }

    // Category function
    private fun openCategory(name: String) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra("CATEGORY_NAME", name)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            val title = data.getStringExtra("title")!!
            val ingredients = data.getStringExtra("ingredients")!!
            val steps = data.getStringExtra("steps")!!
            val isEdit = data.getBooleanExtra("isEdit", false)
            val position = data.getIntExtra("position", -1)

            if (isEdit && position >= 0) {
                val old = recipeList[position]
                recipeList[position] = Recipe(old.img, title, "", ingredients, old.category, old.isFavorite, steps)
            } else {
                recipeList.add(Recipe("", title, "", ingredients, "Uncategorized", 0, steps))
            }

            filterRecipes("") // refresh
        }
    }

    fun deleteRecipe(position: Int) {
        recipeList.removeAt(position)
        filterRecipes("")
    }

    private fun filterRecipes(query: String?) {
        val q = query?.lowercase() ?: ""
        filteredList.clear()
        filteredList.addAll(recipeList.filter {
            it.tittle.lowercase().contains(q) ||
                    it.ing.lowercase().contains(q) ||
                    it.des.lowercase().contains(q)
        })
        adapter.updateList(filteredList)
    }

    private fun filterByDiet(diet: String) {
        filteredList.clear()
        filteredList.addAll(recipeList.filter {
            it.category.contains(diet, ignoreCase = true)
        })
        adapter.updateList(filteredList)
    }
}
