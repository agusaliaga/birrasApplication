package com.santander.birras.controllers;

import com.santander.birras.domain.Meetup;
import com.santander.birras.exceptions.WeatherServiceException;
import com.santander.birras.services.impl.MeetupServiceImpl;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Meetup Controller")
@RestController
@EnableCircuitBreaker
@RequestMapping("/api/birras")
public class MeetupController {

    private final MeetupServiceImpl meetupService;

    public MeetupController(MeetupServiceImpl meetupService) {
        this.meetupService = meetupService;
    }

    /**
     * PUNTO 1)
     * COMO ADMIN CUANTAS CAJAS DE BIRRAS DEBO COMPRAR PARA LA MEETUP.
     * CALCULAR SEGÚN TEMPERATURA Y CANTIDAD DE INSCRIPTOS.
     */
    @ApiOperation(value = "Get the number of beer boxes needed for a meetup")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Calculation Executed Successfully"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach was not found"),
            @ApiResponse(code = 500, message = "An internal error has occurred") })

    @GetMapping(value = "/meetup/calculate/{id}")
    public ResponseEntity getNumberOfBeerBoxesFor(@PathVariable Long id) throws WeatherServiceException {
        Optional<Meetup> meetup = meetupService.findMeetupById(id);
        double numberOfBeerBoxes = meetupService.getNumberOfBeerBoxes(meetup);
        return ResponseEntity.ok("The number of beer boxes needed for the meetup " + meetup.get().getDescription() + " is: " + numberOfBeerBoxes);
    }

    /**
     * PUNTO 4)
     * COMO ADMIN QUIERO ARMAR MEETUPS
     */
    @ApiOperation(value = "Create a new Meetup")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Meetup Created Successfully"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach was not found"),
            @ApiResponse(code = 500, message = "An internal error has occurred") })
    @PostMapping("/meetup")
    public ResponseEntity addMeetup(@RequestBody Meetup meetup) {
        meetupService.addMeetup(meetup);
        return ResponseEntity.ok("Meetup Created");
    }
}
