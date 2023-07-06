package com.bambi.studyenglishapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bambi.studyenglishapp.R
import com.bambi.studyenglishapp.model.WordData


class WordListAdapter(private val wordList: List<WordData>): RecyclerView.Adapter<ViewHolderItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {

        val itemXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.word_recyclerview_list,parent,false)
        return ViewHolderItem(itemXml)
    }

    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
        holder.word.text = wordList[position].english
        holder.meaning.text = wordList[position].japanese
        holder.sentence.text = wordList[position].sentence
        holder.itemView.setOnClickListener {
            val bundle = bundleOf("position" to position + 1)
            findNavController(it).navigate(R.id.action_wordBookFragment_to_wordDetailsFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

}

class ViewHolderItem(itemView: View) :RecyclerView.ViewHolder(itemView){
    val word : TextView =itemView.findViewById(R.id.word)
    val meaning:TextView =itemView.findViewById(R.id.meaning)
    val sentence:TextView =itemView.findViewById(R.id.sentence)
}
