package com.dog.dogsapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dog.dogsapp.R


class ImageAdapter<DogData>(
    private val items: List<DogData>,
    private val onItemClicked: (position: Int) -> Unit,
    private val layoutId:Int
) : RecyclerView.Adapter<ImageAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(layoutId, viewGroup, false)
        return MyViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        val dogData:DogData = items[position]
        val imageUrl:String = dogData.toString()
        Log.i("DogRepository", imageUrl)

        Glide.with(viewHolder.imageView)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .fallback(R.drawable.ic_launcher_background)
            .into(viewHolder.imageView)
    }

    class MyViewHolder(itemView: View, private val onItemClicked: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val imageView: ImageView = itemView.findViewById(R.id.imageview_list_item)
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(view: View) {
            val position = bindingAdapterPosition
            onItemClicked(position)
        }
    }
}

