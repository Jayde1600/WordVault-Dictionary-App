package com.example.wordvault

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wordvault.databinding.ActivityMainBinding
import com.example.wordvault.databinding.DefinitionRecyclerRowBinding

class MeaningAdapter(private var meaningList : List<Meaning>) : RecyclerView.Adapter<MeaningAdapter.MeaningViewHolder>() {

    class MeaningViewHolder(private val binding: DefinitionRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(meaning: Meaning) {
            binding.speechText.text = meaning.partOfSpeech
            binding.definitionView.text = meaning.definitions.joinToString("\n\n") {
                var currentIndex = meaning.definitions.indexOf(it)
                (currentIndex+1).toString()+"."+it.definition.toString()
            }

            if(meaning.synonyms.isEmpty()) {
                binding.synonymsTitletext.visibility = View.GONE
                binding.synonymsView.visibility = View.GONE
            }
            else {
                binding.synonymsTitletext.visibility = View.VISIBLE
                binding.synonymsView.visibility = View.VISIBLE
                binding.synonymsView.text = meaning.synonyms.joinToString ( ", " )
            }

        if(meaning.antonyms.isEmpty()) {
            binding.antonymsTitle.visibility = View.GONE
            binding.antonymsView.visibility = View.GONE
        }
        else {
            binding.antonymsTitle.visibility = View.VISIBLE
            binding.antonymsView.visibility = View.VISIBLE
            binding.antonymsView.text = meaning.antonyms.joinToString ( ", " )
        }
    }

    }

    fun updateNewData(newMeaningList : List<Meaning>) {
        meaningList = newMeaningList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        val binding = DefinitionRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeaningViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return meaningList.size
    }

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
        holder.bind(meaningList[position])
    }
}