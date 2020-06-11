package com.mg.smartrent.user.bootstrap;

import com.mg.smartrent.domain.enums.EnGender;
import com.mg.smartrent.domain.enums.EnUserStatus;
import com.mg.smartrent.domain.models.User;
import com.mg.smartrent.domain.validation.ModelValidationException;
import com.mg.smartrent.user.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Date;

@Configuration
@Log4j2
public class PostConstructor {

    private final UserService userService;

    public PostConstructor(final UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    private void createAdminUser() throws ModelValidationException {
        String adminEmail = "sys.admin@smartrent.com";

        if (!userService.findByEmail(adminEmail).isPresent()) {
            User admin = new User();
            admin.setFirstName("System");
            admin.setLastName("Administrator");
            admin.setPassword("12341234");
            admin.setGender(EnGender.Unknown);
            admin.setEmail(adminEmail);
            admin.setDateOfBirth(new Date());
            admin = userService.create(admin);

            admin.setStatus(EnUserStatus.Active);
            admin.setPassword(null);
            userService.update(admin);

            log.debug("Sys Admin user created.");
        }
    }

}
