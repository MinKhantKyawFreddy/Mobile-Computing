package com.practice.recipesapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.practice.recipesapp.databinding.ActivityAddEditRecipeBinding

class AddEditRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check if editing existing recipe
        val isEdit = intent.getBooleanExtra("isEdit", false)
        val recipeTitle = intent.getStringExtra("title") ?: ""
        val recipeIngredients = intent.getStringExtra("ingredients") ?: ""
        val recipeSteps = intent.getStringExtra("steps") ?: ""

        if (isEdit) {
            binding.etTitle.setText(recipeTitle)
            binding.etIngredients.setText(recipeIngredients)
            binding.etSteps.setText(recipeSteps)
        }

        binding.btnSave.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("title", binding.etTitle.text.toString())
                putExtra("ingredients", binding.etIngredients.text.toString())
                putExtra("steps", binding.etSteps.text.toString())
                putExtra("isEdit", isEdit)
                putExtra("position", intent.getIntExtra("position", -1))
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
