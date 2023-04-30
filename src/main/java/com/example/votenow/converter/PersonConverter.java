package com.example.votenow.converter;


import com.example.votenow.dto.PersonDto;
import com.example.votenow.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonConverter {

    public PersonDto entityToDto(Person person) {
        return new PersonDto(person.getId(), person.getName(),person.getDocument(),person.getVoteType());
    }
}
