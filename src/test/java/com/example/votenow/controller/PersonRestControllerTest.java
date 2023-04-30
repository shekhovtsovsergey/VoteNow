package com.example.voteNOw.controller;


import com.example.votenow.dto.PersonDto;
import com.example.votenow.exaption.PersonNotFoundException;
import com.example.votenow.model.VoteType;
import com.example.votenow.service.PersonService;
import com.example.votenow.exaption.PersonNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
@DisplayName("Контроллер человек")
public class PersonRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    @DisplayName("должен уметь создавать человека")
    public void createPerson() throws Exception {
        PersonDto personDto = new PersonDto(1,"Person","12367",VoteType.YES);
        given(personService.createPerson(personDto)).willReturn(personDto);
        mockMvc.perform(post("/api/v1/person")
                        .contentType("application/json")
                        .content("{\"id\": 1, \"name\": \"Person\",\"document\": \"12367\",\"voteType\": \"YES\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Person")));
        verify(personService).createPerson(personDto);
    }


    @Test
    @DisplayName("должен уметь обновлять человека")
    public void updatePerson() throws Exception {
        PersonDto expectedPerson = new PersonDto(1,"Person","12367",VoteType.YES);
        given(personService.updatePerson(expectedPerson)).willReturn(expectedPerson);
        mockMvc.perform(put("/api/v1/person/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(expectedPerson)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Person"))
                .andExpect(jsonPath("$.document").value("12367"))
                .andExpect(jsonPath("$.voteType").value(VoteType.YES.toString()));

        verify(personService).updatePerson(expectedPerson);
    }


    @Test
    @DisplayName("должен уметь получать список людей")
    public void getPersonList() throws Exception {
        List<PersonDto> expectedPersonList = Arrays.asList(
                PersonDto.builder().id(1).name("Person1").document("Doc1").voteType(VoteType.YES).build(),
                PersonDto.builder().id(2).name("Person2").document("Doc2").voteType(VoteType.NO).build(),
                PersonDto.builder().id(3).name("Person3").document("Doc3").voteType(VoteType.YES).build()
        );

        given(personService.getAllPersons()).willReturn(expectedPersonList);
        mockMvc.perform(get("/api/v1/persons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is("Person1")))
                .andExpect(jsonPath("$[0].document", Matchers.is("Doc1")))
                .andExpect(jsonPath("$[0].voteType", Matchers.is("YES")))
                .andExpect(jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(jsonPath("$[1].name", Matchers.is("Person2")))
                .andExpect(jsonPath("$[1].document", Matchers.is("Doc2")))
                .andExpect(jsonPath("$[1].voteType", Matchers.is("NO")))
                .andExpect(jsonPath("$[2].id", Matchers.is(3)))
                .andExpect(jsonPath("$[2].name", Matchers.is("Person3")))
                .andExpect(jsonPath("$[2].document", Matchers.is("Doc3")))
                .andExpect(jsonPath("$[2].voteType", Matchers.is("YES")));
        verify(personService).getAllPersons();
    }



    @Test
    @DisplayName("должен уметь получать человека по id")
    public void getPersonById() throws Exception {
        PersonDto expectedPerson = PersonDto.builder()
                .id(1)
                .name("John")
                .document("1234567890")
                .voteType(VoteType.YES)
                .build();
        given(personService.getPersonById(1)).willReturn(expectedPerson);
        mockMvc.perform(get("/api/v1/person/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.document").value("1234567890"))
                .andExpect(jsonPath("$.voteType").value("YES"));
        verify(personService).getPersonById(1);
    }


    @Test
    @DisplayName("должен уметь удалять человека по id")
    public void deletePersonById() throws Exception {
        mockMvc.perform(delete("/api/v1/person/1"))
                .andExpect(status().isOk());
        verify(personService).deletePersonById(1);
    }

    @Test
    @DisplayName("должен уметь ловить ошибки и возвращать бэд-реквест")
    public void handleNOtFound_ReturnBadRequest() throws Exception {
        given(personService.getPersonById(1)).willThrow(new PersonNotFoundException("Person NOt Found, check your request"));
        mockMvc.perform(get("/api/v1/person/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Person NOt Found, check your request"));
        verify(personService).getPersonById(1);
    }


    private String toJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}