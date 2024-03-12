package com.example.wordvault

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.wordvault.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SearchBtn.setOnClickListener {
            val word = binding.SearchBtn.text.toString()
            getMeaning(word)
        }

    }

        private fun getMeaning(word : String) {
            GlobalScope.launch {
                val response = RetrofitInstance.dictionaryAPI.getMeaning(word)
                Log.i("Response from API", response.body().toString())
            }

    }


}