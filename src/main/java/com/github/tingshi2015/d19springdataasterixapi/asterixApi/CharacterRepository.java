package com.github.tingshi2015.d19springdataasterixapi.asterixApi;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CharacterRepository extends MongoRepository<Character,String> {


    List<Character> findCharactersByName(String name);

    List<Character> findCharactersByAge(Integer age);

    List<Character> findCharactersByNameAndAge(String name, Integer age);

    List<Character> findCharactersByProfession(String profession);

    List<Character> findCharactersByNameAndProfession(String name, String profession);

    List<Character> findCharactersByAgeAndProfession(Integer age, String profession);

    List<Character> findCharactersByNameAndAgeAndProfession(String name, Integer age, String profession);
}
