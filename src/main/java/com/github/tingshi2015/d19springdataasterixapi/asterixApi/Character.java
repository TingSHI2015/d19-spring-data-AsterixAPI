package com.github.tingshi2015.d19springdataasterixapi.asterixApi;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public record Character(
        String id,
        String name,
        int age,
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
