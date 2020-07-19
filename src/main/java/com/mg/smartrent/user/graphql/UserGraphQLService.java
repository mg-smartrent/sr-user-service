package com.mg.smartrent.user.graphql;

import com.mg.smartrent.domain.models.User;
import com.mg.smartrent.domain.validation.ModelValidationException;
import com.mg.smartrent.user.service.UserService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import io.leangen.graphql.spqr.spring.annotations.WithResolverBuilder;
import org.springframework.stereotype.Service;

@Service
@GraphQLApi
@WithResolverBuilder(AnnotatedResolverBuilder.class)
public class UserGraphQLService {

    private UserService userService;

    public UserGraphQLService(final UserService userService) {
        this.userService = userService;
    }


    @GraphQLMutation
    public User create(@GraphQLArgument(name = "user")
                       @GraphQLNonNull final User user) throws ModelValidationException {
        return userService.create(user);
    }

    @GraphQLMutation
    public User update(@GraphQLArgument(name = "user")
                       @GraphQLNonNull final User user) throws ModelValidationException {
        return userService.update(user);
    }

    @GraphQLMutation
    public User enable(@GraphQLArgument(name = "id")
                       @GraphQLNonNull final String id,
                       @GraphQLArgument(name = "enable")
                       @GraphQLNonNull final Boolean enable) throws ModelValidationException {
        return userService.enable(id, enable);
    }

    @GraphQLMutation
    public User resetPassword(@GraphQLArgument(name = "id")
                              @GraphQLNonNull final String id,
                              @GraphQLArgument(name = "rawPassword") final String rawPassword)
            throws ModelValidationException {
        return userService.resetPassword(id, rawPassword);
    }

    @GraphQLQuery
    public User findById(@GraphQLArgument(name = "id") @GraphQLNonNull final String id) {
        return userService.findById(id).orElse(null);
    }

    @GraphQLQuery
    public User findByEmail(@GraphQLArgument(name = "email") @GraphQLNonNull final String email) {
        return userService.findByEmail(email).orElse(null);
    }
}
