package com.bambi.studyenglishapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bambi.studyenglishapp.R
import com.bambi.studyenglishapp.WordData
import okhttp3.internal.wait


class WordListAdapter(private val wordList: List<WordData>): RecyclerView.Adapter<ViewHolderItem>() {

    lateinit var listener:OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {

        val itemXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.word_recyclerview_list,parent,false)
        return ViewHolderItem(itemXml)
    }

    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
//        val wordList = wordList.value?.get(position) ?: WordData()
        holder.word.text = wordList[position].english
        holder.meaning.text = wordList[position].japanese
        holder.sentence.text = wordList[position].sentence

        holder.itemView.setOnClickListener {
            listener.onItemClickListener(it, position)
        }
    }

    override fun getItemCount(): Int {
        return wordList.size
//        return wordList.value?.size ?: 0
    }

    //インターフェースの作成
    interface OnItemClickListener{
        fun onItemClickListener(view: View, position: Int)
    }

    // リスナー
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

}

class ViewHolderItem(itemView: View) :RecyclerView.ViewHolder(itemView){

    val word : TextView =itemView.findViewById(R.id.word)
    val meaning:TextView =itemView.findViewById(R.id.meaning)
    val sentence:TextView =itemView.findViewById(R.id.sentence)
}
