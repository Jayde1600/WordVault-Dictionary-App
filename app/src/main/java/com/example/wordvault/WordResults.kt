package com.example.wordvault

data class WordResults(
    val word:String,
    val phonetic:String,
    val meanings: List<Meaning>,
)

data class Meaning(
    val partOfSpeech: String,
    val definitions: List<Definition>,
    val synonyms: List<String>,
    val antonyms: List<Any?>,
)

data class Definition(
    val definition: String
)
