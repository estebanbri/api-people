package com.supervielle.people.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Entity
@Immutable
@Table(name = "v_statistics")
@Data
public class PeopleStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private int femaleQuantityTotal;

    private int maleQuantityTotal;

    private int argentinianPercent;

    @JsonIgnore
    private String country;

}
