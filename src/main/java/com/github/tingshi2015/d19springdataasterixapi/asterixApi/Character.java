package com.github.tingshi2015.d19springdataasterixapi.asterixApi;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public record Character(
        String id,
        String name,
        int age,

        // To use the 3rd. filter with Example in 'AsterixController', age muss be 'Integer'!!!
        //Integer age,

        String profession) {
}

/*
@Document("characters")
public record Character(
        @Id String id,
        String name,
        int age,
        String profession) {
}
*/
