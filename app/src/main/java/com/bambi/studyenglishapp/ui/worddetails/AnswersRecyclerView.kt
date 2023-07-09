package com.bambi.studyenglishapp.ui.worddetails


import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bambi.studyenglishapp.R


class AnswersRecyclerView(private val context:Context, private val images: List<Drawable>):RecyclerView.Adapter<ImageViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.answers_recyclerview_list, parent, false)
            return ImageViewHolder(view)
        }

        override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
            val image = images[position]
            holder.imageView.setImageDrawable(image)
            holder.countTitle.text = context.getString(R.string.count, (position + 1).toString())
        }

        override fun getItemCount(): Int {
            return images.size
        }
    }


class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageView:ImageView = itemView.findViewById(R.id.answer_image)
    val countTitle:TextView = itemView.findViewById(R.id.count_title)
}
