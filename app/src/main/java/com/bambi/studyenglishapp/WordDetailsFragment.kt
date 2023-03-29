package com.bambi.studyenglishapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bambi.studyenglishapp.databinding.FragmentWordBookBinding
import com.bambi.studyenglishapp.databinding.FragmentWordDetailsBinding

class WordDetailsFragment : Fragment() {

    private lateinit var binding: FragmentWordDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWordDetailsBinding.inflate(layoutInflater)

        val position = arguments?.getInt("position")

//        val realm = Realm.getDefaultInstance()
//        val wordDataList = realm.where(RealmWordData::class.java).findAll()
//
//        position?.let {
//            binding.word.text = wordDataList[it]?.english
//            binding.meaning.text = wordDataList[it]?.japanese
//            binding.sentence.text = wordDataList[it]?.sentence
//        }
//
//        realm.close()

        return binding.root
    }


}