package com.supervielle.people.repository;

import com.supervielle.people.model.PeopleStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StatisticsRepository extends JpaRepository<PeopleStatistics, Long>{

    @Query(value = "select * from v_statistics where upper(country) = upper(:country)", nativeQuery = true)
    PeopleStatistics findByCountry(@Param("country") String country);

}
