package com.mg.smartrent.user.rest;


import com.mg.smartrent.domain.models.User;
import com.mg.smartrent.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/rest/users", produces = APPLICATION_JSON_VALUE)
public class UsersRestController {

    private final UserService userService;

    public UsersRestController(final UserService userService) {
        this.userService = userService;
    }


    @ApiOperation(value = "Find user by email")
    @GetMapping(params = "email")
    public ResponseEntity<Optional<User>> getUserByEmail(@RequestParam final String email) {
        return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
    }

    @ApiOperation(value = "Find user by Id")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable final String id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }
}
