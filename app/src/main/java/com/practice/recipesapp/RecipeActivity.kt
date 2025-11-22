package com.practice.recipesapp

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import android.content.Intent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.practice.recipesapp.databinding.ActivityRecipeBinding

class RecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeBinding
    private var imgCrop = true

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = DatabaseRepository.dao()
        val recipeId = intent.getIntExtra("RECIPE_ID", -1)
        val recipe = dao.getById(recipeId)

        if (recipe == null) {
            Toast.makeText(this, "Recipe not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Load image
        Glide.with(this).load(recipe.img).into(binding.itemImg)

        // Set title
        binding.tittle.text = recipe.tittle

        // Set steps
        binding.stepData.text = recipe.steps

        // Load ingredients
        val ingList = recipe.ing.split("\n")
        binding.time.text = ingList.getOrNull(0) ?: ""
        binding.ingData.text = ingList.drop(1).joinToString("\n") { "🟢 $it" }

        // Share button
        binding.btnShare.setOnClickListener {
            val shareText = """
                Check out this recipe: ${recipe.tittle}

                Ingredients:
                ${recipe.ing}

                Steps:
                ${recipe.steps}
            """.trimIndent()

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }
            startActivity(Intent.createChooser(shareIntent, "Share recipe via"))
        }

        // Step / Ingredient toggle
        binding.step.background = null
        binding.step.setTextColor(getColor(R.color.black))

        binding.step.setOnClickListener {
            binding.step.setBackgroundResource(R.drawable.btn_ing)
            binding.step.setTextColor(getColor(R.color.white))
            binding.ing.setTextColor(getColor(R.color.black))
            binding.ing.background = null
            binding.stepScroll.visibility = View.VISIBLE
            binding.ingScroll.visibility = View.GONE
        }

        binding.ing.setOnClickListener {
            binding.ing.setBackgroundResource(R.drawable.btn_ing)
            binding.ing.setTextColor(getColor(R.color.white))
            binding.step.setTextColor(getColor(R.color.black))
            binding.step.background = null
            binding.ingScroll.visibility = View.VISIBLE
            binding.stepScroll.visibility = View.GONE
        }

        // Fullscreen toggle
        binding.fullScreen.setOnClickListener {
            if (imgCrop) {
                binding.itemImg.scaleType = ImageView.ScaleType.FIT_CENTER
                Glide.with(this).load(recipe.img).into(binding.itemImg)
                binding.fullScreen.setColorFilter(Color.BLACK)
                binding.shade.visibility = View.GONE
                imgCrop = false
            } else {
                binding.itemImg.scaleType = ImageView.ScaleType.CENTER_CROP
                Glide.with(this).load(recipe.img).into(binding.itemImg)
                binding.fullScreen.setColorFilter(null)
                binding.shade.visibility = View.VISIBLE
                imgCrop = true
            }
        }

        // Back button
        binding.backBtn.setOnClickListener { finish() }

        // Favorite button
        updateFavoriteUI(recipe.isFavorite)
        binding.favoriteBtn.setOnClickListener {
            val newFav = if (recipe.isFavorite == 1) 0 else 1
            dao.setFavorite(recipe.uid, newFav)
            recipe.isFavorite = newFav
            updateFavoriteUI(newFav)
        }
    }

    private fun updateFavoriteUI(isFavorite: Int) {
        if (isFavorite == 1) {
            binding.favoriteBtn.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.favoriteBtn.setImageResource(R.drawable.ic_favorite_border)
        }
    }
}
