package com.mg.smartrent.user.unit


import com.mg.smartrent.user.rest.UsersRestController
import com.mg.smartrent.user.service.UserService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import static com.mg.smartrent.user.ModelBuilder.generateUser


class UserRestControllerTest extends SetupTest {

    @SpringBean
    UserService userService = Mock();

    @Autowired
    UsersRestController usersRestController


    def "test: find user by id"() {
        setup:
        def user = generateUser()
        user.id = "123"
        userService.findById(user.id) >> Optional.of(user)
        when:
        ResponseEntity response = usersRestController.getUserById(user.id)
        then:
        response.getStatusCode() == HttpStatus.OK
        response.getBody().isPresent()
    }

    def "test: find user by email"() {
        setup:
        def user = generateUser()
        user.id = "123"
        userService.findByEmail(user.email) >> Optional.of(user)
        when:
        ResponseEntity response = usersRestController.getUserByEmail(user.email)
        then:
        response.getStatusCode() == HttpStatus.OK
        response.getBody().isPresent()
    }

}
