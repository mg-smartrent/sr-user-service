package com.mg.smartrent.user.unit

import com.mg.persistence.service.Repository
import com.mg.smartrent.domain.models.BizItem
import com.mg.smartrent.domain.models.User
import com.mg.smartrent.domain.validation.ModelBusinessValidationException
import com.mg.smartrent.user.service.UserService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Unroll

import javax.validation.ConstraintViolationException

import static com.mg.smartrent.user.ModelBuilder.generateUser

class UserServiceTest extends SetupTest {

    @SpringBean
    private final Repository<User> userRepositoryMock = Mock()
    @Autowired
    private UserService userService
    @Autowired
    private PasswordEncoder passwordEncoder

    def "test: create user"() {
        setup:
        User user = generateUser()
        user.password = "abc1234"
        userRepositoryMock.findOneBy(_, _, User.class) >> null
        userRepositoryMock.save(_) >> user

        when: "saving a new user"
        User dbUser = userService.create(user)

        then: "successfully saved"
        2 * userRepositoryMock.findOneBy(_, _, User.class)

        dbUser != null
        dbUser.createdDate != null
        dbUser.modifiedDate != null
        passwordEncoder.matches("abc1234", dbUser.getPassword())

    }

    def "test: update user"() {
        setup:
        User user = generateUser()
        user.password = "abc1234"
        user.id = "11111"
        userRepositoryMock.findOneBy(_, _, User.class) >> user
        userRepositoryMock.save(_) >> user

        when:
        User dbUser = userService.update(user)

        then:
        dbUser != null
        dbUser.createdDate != null
        dbUser.modifiedDate != null
        passwordEncoder.matches("abc1234", dbUser.getPassword())
    }

    def "test: enable user"() {
        setup:
        User user = generateUser()
        user.password = "abc1234"
        user.id = "11111"
        userRepositoryMock.findOneBy(_, _, User.class) >> user
        userRepositoryMock.save(_) >> user

        when:
        User dbUser = userService.enable(user.id, true)

        then:
        dbUser != null
    }

    def "test: update user that does not exists"() {
        setup:
        User user = generateUser()
        user.password = "abc1234"
        userRepositoryMock.findOneBy(_, _, User.class) >> null
        userRepositoryMock.save(_) >> user

        when:
        userService.update(user)

        then:
        ModelBusinessValidationException e = thrown()
        e.getMessage() == "User with ID=null not found."
    }

    def "test: find user by ID"() {
        setup:
        def dbUser = generateUser()
        dbUser.id = "123123"
        userRepositoryMock.findOneBy(BizItem.Fields.id, dbUser.id, User.class) >> dbUser

        when:
        def user = userService.findById(dbUser.getId())
        then:
        user.isPresent()

        when:
        user = userService.findById("not_existent")
        then:
        !user.isPresent()
    }

    def "test: find user by email"() {
        setup:
        def dbUser = generateUser()
        dbUser.id = "123123"
        userRepositoryMock.findOneBy(User.Fields.email, dbUser.email, User.class) >> dbUser

        when:
        def user = userService.findByEmail(dbUser.getEmail())

        then:
        user.isPresent()

        when:
        user = userService.findByEmail("inexistent.email@te.com")

        then:
        !user.isPresent()
    }

    @Unroll
    def "test: find user by invalid email #value"() {
        when:
        userService.findByEmail(value)

        then:
        ConstraintViolationException e = thrown()
        e.getMessage().contains(errorMessage)

        where:
        value               | errorMessage
        null                | "findByEmail.arg0: must not be null"
        ""                  | "findByEmail.arg0: must not be blank"
        "invalidEmail"      | "findByEmail.arg0: must be a well-formed email address"
        "inexistent.email@" | "findByEmail.arg0: must be a well-formed email address"

    }
}
