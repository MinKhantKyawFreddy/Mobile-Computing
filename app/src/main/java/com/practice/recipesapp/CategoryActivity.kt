package com.practice.recipesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.recipesapp.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    private lateinit var adapter: PopularAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get category safely
        val category = intent.getStringExtra("CATEGORY_NAME") ?: ""

        // Set category title
        binding.textViewCategoryTitle.text = category

        // Setup back button
        binding.backBtn.setOnClickListener { finish() }

        // Setup RecyclerView with recipes
        setupRecyclerView(category)
    }

    private fun setupRecyclerView(category: String) {
        val dao = DatabaseRepository.dao()

        // Filter recipes by category safely
        val recipes = dao.getAll()
            .filterNotNull()
            .filter { it.category.equals(category, ignoreCase = true) }

        adapter = PopularAdapter(ArrayList(recipes), this)
        binding.rvCategory.layoutManager = LinearLayoutManager(this)
        binding.rvCategory.adapter = adapter
    }
}
