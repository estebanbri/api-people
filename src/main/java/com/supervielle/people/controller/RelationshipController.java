package com.supervielle.people.controller;

import com.supervielle.people.service.familytree.FamilyTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("relationship")
public class RelationshipController {

    @Autowired
    private FamilyTree familyTree;

    @GetMapping("/{id1}/{id2}")
    public ResponseEntity getRelationShip (@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
        familyTree.fillTree();
        String relationship = familyTree.search(id1, id2);
        return ResponseEntity.ok().body(relationship);
    }


}
