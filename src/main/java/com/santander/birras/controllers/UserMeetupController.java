package com.santander.birras.controllers;

import com.santander.birras.domain.UserMeetup;
import com.santander.birras.services.impl.UserMeetupServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "User Meetup Controller")
@RestController
@RequestMapping("/api/birras")
public class UserMeetupController {

    private final UserMeetupServiceImpl userMeetupService;

    public UserMeetupController(UserMeetupServiceImpl userMeetupService) {
        this.userMeetupService = userMeetupService;
    }

    /**
     * PUNTO 4)
     * COMO ADMIN QUIERO INVITAR USUARIOS A UNA MEETUP
     * <p>
     * <p>
     * PUNTO 5)
     * COMO USUARIO INSCRIBIRME EN UNA MEETUP
     **/

    @ApiOperation(value = "Register user to meetup")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully registered user"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach was not found"),
            @ApiResponse(code = 500, message = "An internal error has occurred")
    })
    @PostMapping("/meetup/user")
    public ResponseEntity<?> addUserToMeetup(@RequestBody UserMeetup userMeetup) {
        userMeetupService.addUserToMeetup(userMeetup);
        return ResponseEntity.ok("User added to meetup");
    }

    @ApiOperation(value = "Show all meetups registrations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved meetups"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach was not found"),
            @ApiResponse(code = 500, message = "An internal error has occurred")
    })
    @GetMapping("/meetup/registrations")
    public ResponseEntity<?> showAllMeetupsRegistrations() {
        return ResponseEntity.ok(userMeetupService.showAllMeetupsRegistrations());
    }

    /**
     * PUNTO 5)
     * COMO USUARIO QUIERO HACER CHECKIN EN UNA MEETUP
     **/

    @ApiOperation(value = "Checkin user into meetup")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully checked in user"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach was not found"),
            @ApiResponse(code = 500, message = "An internal error has occurred")
    })
    @PutMapping("/meetup/user/checkin")
    public ResponseEntity<?> checkInMeetup(@RequestBody UserMeetup userMeetup) {
        userMeetupService.checkInUser(userMeetup);
        return ResponseEntity.ok("User checked in");
    }

    /**
     * PUNTO 7)
     * COMO USUARIO QUIERO AVISAR QUE ESTUVE EN LA MEETUP
     **/

    @ApiOperation(value = "User attendance")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully signed user attendance to meeting"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach was not found"),
            @ApiResponse(code = 500, message = "An internal error has occurred")
    })
    @PutMapping("/meetup/user/attended")
    public ResponseEntity<?> attendedMeetup(@RequestBody UserMeetup userMeetup) {
        userMeetupService.userAttended(userMeetup);
        return ResponseEntity.ok("User attendance recorded");
    }
}

