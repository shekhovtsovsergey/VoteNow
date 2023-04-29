package com.example.votenow.service;

import com.example.votenow.converter.PersonConverter;
import com.example.votenow.dao.PersonDao;
import com.example.votenow.dto.PersonDto;
import com.example.votenow.exaption.PersonNotFoundException;
import com.example.votenow.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import com.example.votenow.model.VoteType;

@DisplayName("Сервис человека")
public class PersonServiceTest {

    private PersonServiceImpl personService;

    @Mock
    private PersonDao personDao;

    @Mock
    private PersonConverter personConverter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        personService = new PersonServiceImpl(personDao, personConverter);
    }

    @Test
    @DisplayName("должен уметь отдавать список человек")
    public void testGetAllPersons() {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person(1L, "John", "123456789", VoteType.Yes));
        when(personDao.findAll()).thenReturn(persons);

        List<PersonDto> personDtos = new ArrayList<>();
        personDtos.add(new PersonDto(1L, "John", "123456789", VoteType.Yes));
        when(personConverter.entityToDto(any())).thenReturn(personDtos.get(0));

        List<PersonDto> result = personService.getAllPersons();
        assertEquals(1, result.size());
        assertEquals(personDtos.get(0).getName(), result.get(0).getName());
        assertEquals(personDtos.get(0).getDocument(), result.get(0).getDocument());
        assertEquals(personDtos.get(0).getVoteType(), result.get(0).getVoteType());
    }

    @Test
    @DisplayName("должен уметь удалять человека")
    public void testDeletePersonById() {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person(1L, "John", "123456789", VoteType.Yes));
        when(personDao.findAll()).thenReturn(persons);

        List<PersonDto> personDtos = new ArrayList<>();
        personDtos.add(new PersonDto(1L, "John", "123456789", VoteType.Yes));
        when(personConverter.entityToDto(any())).thenReturn(personDtos.get(0));

        List<PersonDto> result = personService.deletePersonById(1L);
        assertEquals(1, result.size());
        assertEquals(personDtos.get(0).getName(), result.get(0).getName());
        assertEquals(personDtos.get(0).getDocument(), result.get(0).getDocument());
        assertEquals(personDtos.get(0).getVoteType(), result.get(0).getVoteType());
    }

    @Test
    @DisplayName("должен уметь создавать человека")
    public void testCreatePerson() {
        Person person = new Person(1L, "John", "123456789", VoteType.Yes);
        when(personDao.save(any(Person.class))).thenReturn(person);

        PersonDto personDto = new PersonDto(null, "John", "123456789", VoteType.Yes);
        when(personConverter.entityToDto(any())).thenReturn(new PersonDto(1L, "John", "123456789", VoteType.Yes));

        PersonDto result = personService.createPerson(personDto);
        assertEquals("John", result.getName());
        assertEquals("123456789", result.getDocument());
        assertEquals(VoteType.Yes, result.getVoteType());
    }

    @Test
    @DisplayName("должен уметь обновлять человека")
    public void testUpdatePerson() {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person(1L, "John", "123456789", VoteType.Yes));
        when(personDao.findAll()).thenReturn(persons);

        Person person = new Person(1L, "Johnny", "987654321", VoteType.No);
        when(personDao.save(any(Person.class))).thenReturn(person);

        PersonDto personDto = new PersonDto(1L, "Johnny", "987654321", VoteType.No);
        when(personConverter.entityToDto(any())).thenReturn(personDto);

        PersonDto result = personService.updatePerson(personDto);
        assertEquals("Johnny", result.getName());
        assertEquals("987654321", result.getDocument());
        assertEquals(VoteType.No, result.getVoteType());
    }

    @Test
    @DisplayName("должен уметь отдавать человека по id")
    public void testGetPersonById() throws PersonNotFoundException {
        PersonDao personDao = mock(PersonDao.class);
        PersonConverter personConverter = mock(PersonConverter.class);

        when(personDao.findById(1L)).thenReturn(Optional.of(new Person(1L, "John", "123456789", VoteType.Yes)));
        when(personConverter.entityToDto(any(Person.class))).thenReturn(new PersonDto(1L, "John", "123456789", VoteType.Yes));

        PersonService personService = new PersonServiceImpl(personDao, personConverter);
        PersonDto personDto = personService.getPersonById(1L);

        assertNotNull(personDto);
        assertEquals(personDto.getName(), "John");
        assertEquals(personDto.getDocument(), "123456789");
        assertEquals(personDto.getVoteType(), VoteType.Yes);
    }



    @Test
    @DisplayName("должен уметь возвращать ошибку если идентификатор человека не найден")
    public void testGetPersonByIdNotFound() {
        when(personDao.findById(1L)).thenReturn(Optional.empty());
        try {
            personService.getPersonById(1L);
        } catch (PersonNotFoundException e) {
            assertEquals("Person id 1 not found", e.getMessage());
        }
        verify(personDao, times(1)).findById(1L);
        verify(personConverter, never()).entityToDto(any());
    }


}