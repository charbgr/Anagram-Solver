package com.bmpak.anagramsolver.framework.data.anagram

import com.bmpak.anagramsolver.model.Anagram
import com.bmpak.anagramsolver.model.Dictionary

interface AnagramEntityMapper {
  fun transform(entity: AnagramEntity): Anagram
  fun transform(entities: List<AnagramEntity>) : List<Anagram> = entities.map { transform(it) }
}

object RealAnagramEntityMapper : AnagramEntityMapper {
  override fun transform(entity: AnagramEntity): Anagram =
      Anagram(word = entity.wordOrigin, dictionary = Dictionary.from(entity.dictionary))
}
