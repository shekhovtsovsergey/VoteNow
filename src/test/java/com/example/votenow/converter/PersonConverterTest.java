package com.example.votenow.converter;

import com.example.votenow.dto.PersonDto;
import com.example.votenow.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.example.votenow.model.VoteType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PersonConverterTest {

    @Mock
    private Person person;

    @Mock
    private PersonDto personDto;

    @InjectMocks
    private PersonConverter personConverter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(person.getId()).thenReturn(1L);
        when(person.getName()).thenReturn("John");
        when(person.getDocument()).thenReturn("123456789");
        when(person.getVoteType()).thenReturn(VoteType.Yes);
        when(personDto.getId()).thenReturn(1L);
        when(personDto.getName()).thenReturn("John");
        when(personDto.getDocument()).thenReturn("123456789");
        when(personDto.getVoteType()).thenReturn(VoteType.Yes);
    }

    @Test
    public void entityToDto_should_return_personDto_with_same_properties_as_person() {
        PersonDto resultDto = personConverter.entityToDto(person);
        assertEquals(person.getId(), resultDto.getId());
        assertEquals(person.getName(), resultDto.getName());
        assertEquals(person.getDocument(), resultDto.getDocument());
        assertEquals(person.getVoteType(), resultDto.getVoteType());
    }
}
