package com.github.tingshi2015.d19springdataasterixapi.asterixApi;

import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/asterix")
public class AsterixController {
    private final CharacterRepository characterRepository;

    public AsterixController(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    //this only get the fest List from our computer! not from MongoDB
    @GetMapping("/characters")
    public List<Character> getCharacters(){
        return List.of(
                new Character("1", "Asterix", 35, "Krieger"),
                new Character("2", "Obelix", 35, "Lieferant"),
                new Character("3", "Miraculix", 60, "Druide"),
                new Character("4", "Majestix", 60, "Häuptling"),
                new Character("5", "Troubadix", 25, "Barden"),
                new Character("6", "Gutemine", 35, "Häuptlingsfrau"),
                new Character("7", "Idefix", 5, "Hund"),
                new Character("8", "Geriatrix", 70, "Rentner"),
                new Character("9", "Automatix", 35, "Schmied"),
                new Character("10", "Grockelix", 35, "Fischer")
        );
    }

    //----Duplicated Endpoint with the first @GetMapping("/characters")-----get from MongoDB!
/*    @GetMapping("/characters")
    public List<Character> getAllCharacters(){
        return characterRepository.findAll();
    }*/

    @GetMapping("/characters/{id}")
    public Character getCharacterById(@PathVariable String id){
        return characterRepository.findById(id)
                .orElseThrow();  // .orElseThrow(()->new NoSuchElementException("Character with id:" + id +" not found."));

    }

//----Method 1: filter with steam (Low efficiency for big DB!)-------
//'age' in Controller can keep to be 'int' !!!
    @GetMapping("/characters1")       //error: @GetMapping("/characters")!Duplicated Endpoint with the first @GetMapping("/characters")
    public List<Character> getAllCharactersWithStream(@RequestParam(required = false) String name,
                                        @RequestParam(required = false) Integer age,              //false: 'int' age!
                                        @RequestParam(required = false) String profession){

        return characterRepository.findAll().stream()
                .filter(character -> name==null || character.name().equals(name))
                .filter(character -> age==0 || character.age() == age)
                .filter(character -> profession==null || character.profession().equals(profession))
                .toList();
    }

//----Method 2: filter with Repository (Low efficiency for big DB!)-------
//'age' in Controller can keep to be 'int' !!!
@GetMapping("/api/asterix/characters2")
public List<Character> getAllCharactersWithRepository(@RequestParam(required = false) String name,
                                                      @RequestParam(required = false) Integer age,
                                                      @RequestParam(required = false) String profession) {
    if (name == null && age == null && profession == null) {
        return characterRepository.findAll();
    }
    else if (name != null && age == null && profession == null) {
        return characterRepository.findCharactersByName(name);
    }
    else if (name == null && age != null && profession == null) {
        return characterRepository.findCharactersByAge(age);
    }
    else if (name != null && age != null && profession == null) {
        return characterRepository.findCharactersByNameAndAge(name, age);
    }
    else if (name == null && age == null) {
        return characterRepository.findCharactersByProfession(profession);
    }
    else if (name != null && age == null) {
        return characterRepository.findCharactersByNameAndProfession(name, profession);
    }
    else if (name == null) {
        return characterRepository.findCharactersByAgeAndProfession(age, profession);
    }
    else {
        return characterRepository.findCharactersByNameAndAgeAndProfession(name, age, profession);
    }
}

//----Method 3: filter with Example ('age' in Controller must be 'Integer')-------
/*@GetMapping("/api/asterix/characters3")
public List<Character> getAllCharactersWithExample(@RequestParam(required = false) String name,
                                                   @RequestParam(required = false) Integer age,
                                                   @RequestParam(required = false) String profession) {

    return characterRepository.findAll(Example.of(new Character(null, name, age, profession)));
}*/





    @PostMapping("/characters")
    @ResponseStatus(HttpStatus.CREATED)
    public Character createCharacter(@RequestBody Character character){
        return characterRepository.save(character);
    }

    @PutMapping("/characters/{id}")
    public Character updateCharacterById(@PathVariable String id, @RequestBody Character character){
        if(characterRepository.existsById(id)){
            return characterRepository.save(character);
        }
        //throw new NoSuchElementException("Can't update Character with id:" + id + ", Character does not exist.");
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't update Character with id:" + id + ", Character does not exist.");
    }

    @DeleteMapping("/characters/{id}")
    public void deleteCharacterById(@PathVariable String id){
        if(characterRepository.existsById(id)){
            characterRepository.deleteById(id);
        }
        throw new NoSuchElementException("Can't delete Character with id:" + id + ", Character does not exist.");
        //throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't delete Character with id:" + id + ", Character does not exist.");
    }



}
