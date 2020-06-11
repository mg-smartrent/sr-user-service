package com.mg.smartrent.user.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.util.HashMap;

public class GQLVariables {
    private final ObjectMapper mapper = new ObjectMapper();
    private final HashMap<String, Object> variables = new HashMap<>();


    public GQLVariables add(final String name, final Object value) {
        variables.put(name, value);
        return this;
    }

    @SneakyThrows
    public String toString() {
        return mapper.writeValueAsString(variables);
    }
}
