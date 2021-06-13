package com.supervielle.people.service.statistics.impl;

import com.supervielle.people.model.PeopleStatistics;
import com.supervielle.people.repository.StatisticsRepository;
import com.supervielle.people.service.statistics.PeopleStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PeopleStatisticsServiceImpl implements PeopleStatisticsService {

    @Value("${country.statistics}")
    private String country;

    @Autowired
    private StatisticsRepository repository;

    @Override
    public PeopleStatistics getStatistics() {
        PeopleStatistics peopleStatistics = repository.findByCountry(country);
        if(peopleStatistics == null) return new PeopleStatistics();
        return peopleStatistics;
    }

}
