package com.example.votenow.controller;


import com.example.votenow.dto.PersonDto;
import com.example.votenow.exaption.PersonNotFoundException;
import com.example.votenow.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PersonRestController {

    private final PersonService personService;

    @GetMapping("/api/v1/persons")
    public List<PersonDto> getPersonList() {
        return personService.getAllPersons();
    }

    @GetMapping("/api/v1/person/{id}")
    public PersonDto getPersonById(@PathVariable(name = "id") Integer id) throws PersonNotFoundException {
        return personService.getPersonById(id);
    }

    @DeleteMapping("/api/v1/person/{id}")
    public void deletePersonById(@PathVariable(name = "id") Integer id) {
        personService.deletePersonById(id);
    }

    @PutMapping("/api/v1/person/{id}")
    public PersonDto updatePerson(@Validated @RequestBody PersonDto PersonDto)  {
        return personService.updatePerson(PersonDto);
    }

    @PostMapping("/api/v1/person")
    public PersonDto createPerson(@Validated @RequestBody PersonDto PersonDto) {
        return personService.createPerson(PersonDto);
    }

    @ExceptionHandler({PersonNotFoundException.class})
    private ResponseEntity<String> handleNotFound(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
