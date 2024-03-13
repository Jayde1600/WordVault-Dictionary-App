package com.example.wordvault

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wordvault.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    lateinit var binding: ActivityMainBinding

    lateinit var adapter: MeaningAdapter

    private lateinit var textToSpeech: TextToSpeech

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

        textToSpeech = TextToSpeech(this, this)

        // Set click listener for pronunciation button
        binding.pronunciationButton.setOnClickListener {
            val wordToPronounce = binding.wordUp.text.toString()
            pronounceWord(wordToPronounce)
        }
    }

    // TextToSpeech engine initialization callback
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Set language to default locale
            val result = textToSpeech.setLanguage(Locale.getDefault())
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Language not supported
                Toast.makeText(this, "Language not supported", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Initialization failed
            Toast.makeText(this, "TextToSpeech initialization failed", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to pronounce the word
    private fun pronounceWord(word: String) {
        textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    // Release TextToSpeech engine when activity is destroyed
    override fun onDestroy() {
        super.onDestroy()
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
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



