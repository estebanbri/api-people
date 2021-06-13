package com.supervielle.people.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supervielle.people.dto.ContactDataDTO;
import com.supervielle.people.dto.PersonDTOAdd;
import com.supervielle.people.exception.PeopleException;
import com.supervielle.people.model.Person;
import com.supervielle.people.model.PersonId;
import com.supervielle.people.model.enums.Gender;
import com.supervielle.people.repository.PeopleCrudRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static junit.framework.TestCase.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@EnableWebMvc
class PeopleCrudControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PeopleCrudRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findById() throws Exception {

        when(repository.findById(any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/people").contentType(MediaType.APPLICATION_JSON)
                .queryParam("dni", "123")
                .queryParam("country", "ARGENTINA")
                .queryParam("dniType", "DNI")
                .queryParam("gender", "MALE"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PeopleException));
    }


    @Test
    void save() throws Exception {
        when(repository.save(any())).thenReturn(getPersonMock());

        mockMvc.perform(post("/people")
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(getPersonDTOAddMock()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }


    private Person getPersonMock() {
        Person person = new Person();
        PersonId id = new PersonId();
        id.setDni(123L);
        id.setDniType("DNI");
        id.setCountry("ARGENTINA");
        id.setGender(Gender.MALE);
        person.setPersonId(id);
        return person;
    }

    private PersonDTOAdd getPersonDTOAddMock() {
        PersonDTOAdd personDTOAdd = new PersonDTOAdd();
        personDTOAdd.setFirstName("FIRST");
        personDTOAdd.setLastName("LAST");
        personDTOAdd.setDni(123L);
        personDTOAdd.setDniType("DNI");
        personDTOAdd.setCountry("ARGENTINA");
        personDTOAdd.setGender(Gender.MALE.toString());
        ContactDataDTO contactData = new ContactDataDTO();
        contactData.setEmail("a@a");
        contactData.setPhoneNumber("123");
        personDTOAdd.setContactData(contactData);
        personDTOAdd.setBirthDate(LocalDate.of(1989, Month.FEBRUARY, 25));
        return personDTOAdd;
    }


    private PersonDTOAdd getPersonDTOAddMockEmpty() {
        return new PersonDTOAdd();
    }
}