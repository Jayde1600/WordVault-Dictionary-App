package com.example.wordvault

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wordvault.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var adapter: MeaningAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SearchBtn.setOnClickListener {
            val word = binding.SearchInput.text.toString()
            getMeaning(word)
        }
        adapter = MeaningAdapter(emptyList())
        binding.recyclerMeaning.layoutManager = LinearLayoutManager(this)
        binding.recyclerMeaning.adapter = adapter
    }

        private fun getMeaning(word : String) {
            setInProgress(true)
            GlobalScope.launch {

                try {
                    val response = RetrofitInstance.dictionaryAPI.getMeaning(word)
                    if(response.body() == null) {
                        throw(Exception())
                    }
                    runOnUiThread {
                        setInProgress(false)
                        response.body()?.first()?.let {
                            setUI(it)
                        }
                    }

                } catch (e : Exception) {
                    runOnUiThread {
                        setInProgress(false)
                        Toast.makeText(applicationContext, "Something went wrong :(", Toast.LENGTH_SHORT).show()
                    }
                }

                val response = RetrofitInstance.dictionaryAPI.getMeaning(word)
                runOnUiThread {
                    setInProgress(false)
                    response.body()?.first()?.let {
                        setUI(it)
                    }
                }
            }
    }

    private fun setUI(response : WordResults) {
        binding.wordUp.text = response.word
        binding.underWord.text = response.phonetic
        adapter.updateNewData(response.meanings)
    }

    private fun setInProgress(InProgress : Boolean) {
        if(InProgress) {
            binding.SearchBtn.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        }
        else {
            binding.SearchBtn.visibility = View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

}



