package com.example.votenow.service;

import com.example.votenow.dto.PersonDto;
import com.example.votenow.exaption.PersonNotFoundException;
import java.util.List;

public interface PersonService {

    List<PersonDto> getAllPersons();
    List<PersonDto> deletePersonById(Long id);
    PersonDto createPerson(PersonDto personDto) ;
    PersonDto updatePerson(PersonDto personDto) ;
    PersonDto getPersonById(Long id) throws PersonNotFoundException;
}
