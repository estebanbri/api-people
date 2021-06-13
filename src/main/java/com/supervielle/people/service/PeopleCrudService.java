package com.supervielle.people.service;

import com.supervielle.people.dto.PersonDTOAdd;
import com.supervielle.people.dto.PersonDTOUpdate;
import com.supervielle.people.dto.PersonIdDTO;
import com.supervielle.people.dto.RelationShipDTO;
import com.supervielle.people.model.Person;

public interface PeopleCrudService {

    PersonDTOAdd findById(PersonIdDTO id);

    Person save(PersonDTOAdd personDTOAdd);

    PersonDTOAdd updateById(PersonIdDTO id, PersonDTOUpdate personDTOAdd);

    void deleteById(PersonIdDTO id);

    RelationShipDTO saveRelationship(RelationShipDTO relationShipDTO);
}
