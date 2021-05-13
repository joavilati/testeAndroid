package com.jhonata.catapp.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jhonata.catapp.R
import com.jhonata.catapp.databinding.ItemBreedBinding
import com.jhonata.catapp.model.Breed
const val TAG = "BreedListAdapter"
class BreedListAdapter( val goToDetail: (Breed, Int)->Unit ):RecyclerView.Adapter<BreedListAdapter.ItemBreedViewHolder>() {
    private var breedList = arrayListOf<Breed>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemBreedViewHolder {
        val binding = ItemBreedBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ItemBreedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemBreedViewHolder, position: Int) {
        holder.bind(breedList[position], position)
    }

    override fun getItemCount() = breedList.size

    fun addBreeds(newBreeds: List<Breed>){
        breedList.clear()
        breedList.addAll(newBreeds)
        notifyItemRangeChanged(breedList.size-1, newBreeds.size)
    }

    inner class ItemBreedViewHolder(
        private val binding:ItemBreedBinding
        ):RecyclerView.ViewHolder(binding.root){

        fun bind(breed:Breed, position: Int) {

            val set = ConstraintSet()
            set.clone(binding.clInsideCard)
            if(breed.image.url.isEmpty()){
                Log.d(TAG, "Ninja Breed without url = ${breed.name}")
                set.setDimensionRatio(binding.ivBreed.id,"1:1")
            }else {
                set.setDimensionRatio(binding.ivBreed.id,breed.image.aspectRatio.toString())
            }

            set.applyTo(binding.clInsideCard)
            Glide.with(binding.root.context)
                .load(breed.image.url)
                .error(R.drawable.ic_baseline_error_24)
                .placeholder(R.drawable.kitty)
                .into(binding.ivBreed)
            binding.tvName.text = breed.name
            binding.tvOrigin.text = breed.origin

            binding.cvBreed.setOnClickListener {
                goToDetail(breed, position)
            }
        }
    }
}