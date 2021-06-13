package com.supervielle.people.service.impl;

import com.supervielle.people.dto.*;
import com.supervielle.people.model.*;
import com.supervielle.people.model.enums.Gender;
import com.supervielle.people.repository.PeopleCrudRepository;
import com.supervielle.people.service.PeopleCrudService;
import com.supervielle.people.exception.ApiExceptionResponse;
import com.supervielle.people.exception.PeopleException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class PeopleCrudServiceImpl implements PeopleCrudService {

    @Autowired
    private PeopleCrudRepository peopleCrudRepository;

    @Override
    public PersonDTOAdd findById(PersonIdDTO id) {
        Person person = getPersonById(id);
        log.info("Person with id = {} successfully retrieved ...", person.getPersonId());
        return createPersonDTO(person);
    }

    @Override
    @Transactional
    public Person save(PersonDTOAdd personDTOAdd) {
        Person person = createPerson(personDTOAdd);
        Person personNew = peopleCrudRepository.save(person);
        log.info("Person with id = {} successfully saved ...", personNew.getPersonId());
        return personNew;
    }

    @Override
    @Transactional
    public PersonDTOAdd updateById(PersonIdDTO id, PersonDTOUpdate personDTOUpdate) {
        final Person person = getPersonById(id);
        BeanUtils.copyProperties(personDTOUpdate, person);
        BeanUtils.copyProperties(personDTOUpdate.getContactData(), person.getContactData());
        return createPersonDTO(peopleCrudRepository.save(person));
    }

    @Override
    @Transactional
    public void deleteById(PersonIdDTO id) {
        peopleCrudRepository.delete(getPersonById(id));
    }

    @Override
    @Transactional
    public RelationShipDTO saveRelationship(RelationShipDTO relationShipDTO) {

        Optional<Person> person = peopleCrudRepository.findById(relationShipDTO.getSonId());

        if(!person.isPresent()) throw new PeopleException(ApiExceptionResponse.createWithDetail("Person not found"));

        person.get().setParentId(relationShipDTO.getParentId());

        return relationShipDTO;
    }

    private Person createPerson(PersonDTOAdd personDTOAdd) {
        Person person = new Person();
        PersonId id = new PersonId();
        ContactData contactData = new ContactData();
        BeanUtils.copyProperties(personDTOAdd, person);
        BeanUtils.copyProperties(personDTOAdd, id);
        BeanUtils.copyProperties(personDTOAdd.getContactData() , contactData);
        id.setGender(Gender.valueOf(StringUtils.upperCase(personDTOAdd.getGender())));
        person.setPersonId(id);
        person.setContactData(contactData);
        return person;
    }

    private PersonDTOAdd createPersonDTO(Person person) {
        PersonDTOAdd personDTOAdd = new PersonDTOAdd();
        BeanUtils.copyProperties(person, personDTOAdd);
        BeanUtils.copyProperties(person.getPersonId(), personDTOAdd);
        personDTOAdd.setGender(person.getPersonId().getGender().toString());
        ContactDataDTO contactData = new ContactDataDTO();
        BeanUtils.copyProperties(person.getContactData() , contactData);
        personDTOAdd.setContactData(contactData);
        return personDTOAdd;
    }

    private Person getPersonById(PersonIdDTO id) {
        final Optional<Person> person;
        person = peopleCrudRepository.findById(id.getDni(),
                    id.getDniType(),
                    id.getCountry(),
                    id.getGender());
       if(!person.isPresent()) throw new PeopleException(ApiExceptionResponse.createWithDetail("Person not found"));
       return person.get();
    }


}
