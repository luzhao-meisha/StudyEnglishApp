package com.bambi.studyenglishapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bambi.studyenglishapp.R
import com.bambi.studyenglishapp.WordData
import okhttp3.internal.wait


class WordListAdapter(private val wordList: MutableList<WordData>): RecyclerView.Adapter<ViewHolderItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {

        val itemXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.word_recyclerview_list,parent,false)
        return ViewHolderItem(itemXml)
    }

    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
        holder.word.text = wordList[position].english
        holder.meaning.text = wordList[position].japanese
        holder.sentence.text = wordList[position].sentence
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
