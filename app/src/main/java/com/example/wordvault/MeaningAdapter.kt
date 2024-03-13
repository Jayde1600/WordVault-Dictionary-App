package com.example.wordvault

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wordvault.databinding.ActivityMainBinding
import com.example.wordvault.databinding.DefinitionRecyclerRowBinding

class MeaningAdapter(private var meaningList : List<Meaning>) : RecyclerView.Adapter<MeaningAdapter.MeaningViewHolder>() {

    class MeaningViewHolder(private val binding: DefinitionRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(meaning: Meaning) {
            binding.speechText.text = meaning.partOfSpeech
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