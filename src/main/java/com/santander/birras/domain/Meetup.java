package com.santander.birras.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "meetup")
public class Meetup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meetup_id")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "meetup")
    @Column(name = "users")
    @JsonIgnore
    private Set<UserMeetup> users = new HashSet<>();

    @Column(name = "meetup_date")
    private String dateTime;

    @Column(name = "meetup_description")
    private String description;

    @Column(name = "meetup_city")
    private String city;
}
