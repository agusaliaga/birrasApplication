package com.santander.birras.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "user_meetup")
public class UserMeetup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_meetup_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meetup_id")
    private Meetup meetup;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "checkIn")
    private boolean checkIn = false;

    @Column(name = "attended")
    private boolean attended = false;
}
