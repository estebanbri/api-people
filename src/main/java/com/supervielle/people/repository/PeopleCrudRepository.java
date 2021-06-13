package com.supervielle.people.repository;

import com.supervielle.people.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PeopleCrudRepository extends JpaRepository<Person, Long> {

    @Query("from Person p " +
            " where p.personId.dni = :dni" +
            " and upper(p.personId.dniType) = upper(:dniType)" +
            " and upper(p.personId.country) = upper(:country)" +
            " and upper(p.personId.gender) = upper(:gender)"
    )
    Optional<Person> findById(@Param("dni") Long dni,
                              @Param("dniType") String dniType,
                              @Param("country") String country,
                              @Param("gender") String gender);

    @Query("from Person where id = :id")
    Optional<Person> findById(@Param("id") Long id);
}
