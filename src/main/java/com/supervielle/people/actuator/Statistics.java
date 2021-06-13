package com.supervielle.people.actuator;

import com.supervielle.people.model.PeopleStatistics;
import com.supervielle.people.service.statistics.PeopleStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Endpoint(id="statistics")
@Component
public class Statistics {

    @Autowired
    private PeopleStatisticsService service;

    @ReadOperation
    public PeopleStatistics statistics() {
        return service.getStatistics();
    }
}