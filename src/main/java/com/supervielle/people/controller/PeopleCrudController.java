package com.supervielle.people.controller;

import com.supervielle.people.dto.PersonDTOAdd;
import com.supervielle.people.dto.PersonDTOUpdate;
import com.supervielle.people.dto.PersonIdDTO;
import com.supervielle.people.dto.RelationShipDTO;
import com.supervielle.people.model.Person;
import com.supervielle.people.service.PeopleCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("people")
public class PeopleCrudController {

    @Autowired
    private PeopleCrudService service;


    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("dni") Long dni,
                                      @RequestParam("dniType") String dniType,
                                      @RequestParam("country") String country,
                                      @RequestParam("gender") String gender) {

        PersonIdDTO personIdDTO = PersonIdDTO.builder()
                                    .dni(dni)
                                    .country(country)
                                    .dniType(dniType)
                                    .gender(gender)
                                    .build();

        PersonDTOAdd add = service.findById(personIdDTO);
        return ResponseEntity.ok(add);
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody PersonDTOAdd personDTOAdd) {
        final Person person = service.save(personDTOAdd);
        String[] queryParams = {
                person.getPersonId().getDni().toString(),
                person.getPersonId().getCountry(),
                person.getPersonId().getDniType(),
                person.getPersonId().getGender().toString()
        };
        return ResponseEntity.created(getCurrentUri(queryParams))
                .build();
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestParam("dni") Long dni,
                                    @RequestParam("dniType") String dniType,
                                    @RequestParam("country") String country,
                                    @RequestParam("gender") String gender,
                                    @Valid @RequestBody PersonDTOUpdate personDTOUpdate){

        PersonIdDTO personIdDTO = PersonIdDTO.builder()
                .dni(dni)
                .country(country)
                .dniType(dniType)
                .gender(gender)
                .build();


        return ResponseEntity.ok().body(service.updateById(personIdDTO, personDTOUpdate));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam("dni") Long dni,
                                    @RequestParam("dniType") String dniType,
                                    @RequestParam("country") String country,
                                    @RequestParam("gender") String gender){

        PersonIdDTO personIdDTO = PersonIdDTO.builder()
                .dni(dni)
                .country(country)
                .dniType(dniType)
                .gender(gender)
                .build();

        service.deleteById(personIdDTO);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{parent_id}/parent/{son_id}")
    public ResponseEntity<?> saveRelationship(@PathVariable("parent_id") Long parentId, @PathVariable("son_id") Long sonId) {

        RelationShipDTO relationShipDTO = RelationShipDTO.builder()
                .parentId(parentId)
                .sonId(sonId)
                .build();

        service.saveRelationship(relationShipDTO);
        return ResponseEntity.ok().build();
    }

    private URI getCurrentUri(String... queryValue) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .queryParam("dni", queryValue[0])
                .queryParam("country", queryValue[1])
                .queryParam("dniType", queryValue[2])
                .queryParam("gender", queryValue[3])
                .build()
                .toUri();
    }

    private URI getCurrentUri(String path, Object... expandedValues) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path(path)
                .buildAndExpand(expandedValues)
                .toUri();
    }

}
