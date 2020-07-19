package com.mg.smartrent.user.unit

import com.mg.smartrent.user.graphql.UserGraphQLService
import com.mg.smartrent.user.service.UserService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired

import static com.mg.smartrent.user.ModelBuilder.generateUser

class UserServiceGraphQLTest extends SetupTest {

    @SpringBean
    UserService userService = Mock();

    @Autowired
    UserGraphQLService userGraphQLService


    def "test: create user"() {
        setup:
        def user = generateUser()
        userService.create(user) >> user

        when: "saving a new user"
        def dbUser = userGraphQLService.create(user)

        then: "successfully saved"
        dbUser != null
    }

    def "test: update user"() {
        setup:
        def user = generateUser()
        userService.update(user) >> user
        when:
        def dbUser = userGraphQLService.update(user)
        then:
        dbUser != null
    }

    def "test: find user by id"() {
        setup:
        def user = generateUser()
        user.id = "123"
        userService.findById(user.id) >> Optional.of(user)
        when:
        def dbUser = userGraphQLService.findById(user.id)
        then:
        dbUser != null
    }

    def "test: find user by email"() {
        setup:
        def user = generateUser()
        user.id = "123"
        userService.findByEmail(user.email) >> Optional.of(user)
        when:
        def dbUser = userGraphQLService.findByEmail(user.email)
        then:
        dbUser != null
    }

}
