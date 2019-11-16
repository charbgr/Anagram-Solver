package com.bmpak.anagramsolver.framework.data.anagram

import com.bmpak.anagramsolver.model.Anagram

interface AnagramEntityMapper {
  fun transform(entity: AnagramEntity): Anagram
  fun transform(entities: List<AnagramEntity>) : List<Anagram> {
    return entities.map { transform(it) }
  }
}

object RealAnagramEntityMapper : AnagramEntityMapper {
  override fun transform(entity: AnagramEntity): Anagram = Anagram(entity.value)
}
