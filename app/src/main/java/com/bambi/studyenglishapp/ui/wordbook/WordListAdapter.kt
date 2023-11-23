package com.bambi.studyenglishapp.ui.wordbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bambi.studyenglishapp.R
import com.bambi.studyenglishapp.model.WordData


class WordListAdapter : RecyclerView.Adapter<ViewHolderItem>() {

    private var wordList: List<WordData> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {

        val itemXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.word_recyclerview_list, parent, false)
        return ViewHolderItem(itemXml)
    }

    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
        holder.word.text = wordList[position].english
        holder.meaning.text = wordList[position].japanese
        holder.sentence.text = wordList[position].sentence
        holder.itemView.setOnClickListener {
            val id = wordList[position].id
            val bundle = Bundle()
            bundle.putInt("position",id.toInt())
            findNavController(it).navigate(
                R.id.action_wordBookFragment_to_wordDetailsFragment,
                bundle
            )
        }
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    fun setWords(words: List<WordData>) {
        wordList = words
        notifyDataSetChanged()
    }

}

class ViewHolderItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val word: TextView = itemView.findViewById(R.id.word)
    val meaning: TextView = itemView.findViewById(R.id.meaning)
    val sentence: TextView = itemView.findViewById(R.id.sentence)
}