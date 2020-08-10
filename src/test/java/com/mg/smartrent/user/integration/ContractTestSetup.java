package com.mg.smartrent.user.integration;

import com.mg.smartrent.domain.models.User;
import com.mg.smartrent.user.Application;
import com.mg.smartrent.user.service.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.Optional;

import static com.mg.smartrent.domain.enums.EnGender.Unknown;
import static com.mg.smartrent.domain.enums.EnUserStatus.Active;

/**
 * This is an implementation of Spring Cloud contract.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class ContractTestSetup {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    UserService userService;

    static {
        System.setProperty("eureka.client.enabled", "false");
        System.setProperty("spring.cloud.config.failFast", "false");
    }

    @Before
    @SneakyThrows
    public void setup() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        User user = buildDefaultUser();
        Mockito.when(userService.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userService.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userService.resetPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(user);

        User createdUser = buildDefaultUser();
        createdUser.setEnabled(false);
        Mockito.when(userService.create(Mockito.any(User.class))).thenReturn(createdUser);

        User updatedUser = buildDefaultUser();
        updatedUser.setEnabled(true);
        Mockito.when(userService.update(Mockito.any(User.class))).thenReturn(updatedUser);

        User enabledUser = buildDefaultUser();
        enabledUser.setEnabled(true);
        Mockito.when(userService.enable(Mockito.anyString(), Mockito.eq(true))).thenReturn(enabledUser);

        User disabledUser = buildDefaultUser();
        disabledUser.setEnabled(false);
        Mockito.when(userService.enable(Mockito.anyString(), Mockito.eq(false))).thenReturn(disabledUser);
    }

    private User buildDefaultUser() {
        User user = new User();
        user.setId("000000000000000000000000");
        user.setStatus(Active);
        user.setEmail("sys.admin@smartrent.com");
        user.setLastName("Administrator");
        user.setFirstName("System");
        user.setModifiedDate(new Date(1595058926977L));
        user.setCreatedDate(new Date(1595058926977L));
        user.setDateOfBirth(new Date(1595058926977L));
        user.setEnabled(false);
        user.setGender(Unknown);

        return user;
    }
}

