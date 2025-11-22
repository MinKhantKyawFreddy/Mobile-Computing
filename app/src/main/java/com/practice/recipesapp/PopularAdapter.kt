package com.practice.recipesapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practice.recipesapp.databinding.PopularRvItemBinding

class PopularAdapter(
    private var dataList: ArrayList<Recipe>,
    private val context: Context
) : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: PopularRvItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PopularRvItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = dataList[position]

        Glide.with(context).load(recipe.img).into(holder.binding.popularImg)
        holder.binding.popularTxt.text = recipe.tittle

        val time = recipe.ing.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        holder.binding.popularTime.text = time.getOrNull(0) ?: ""

        holder.itemView.setOnClickListener {
            val intent = Intent(context, RecipeActivity::class.java)
            intent.putExtra("RECIPE_ID", recipe.uid) // Pass recipe ID
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
}
