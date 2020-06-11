package com.mg.smartrent.user.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

public class GQLArguments {
    private final ObjectMapper mapper = new ObjectMapper();
    private final List<Argument> arguments = new ArrayList<>();

    public GQLArguments add(final String name, final Object value) {
        arguments.add(new Argument(name, value));
        return this;
    }

    @SneakyThrows
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        for (Argument argument : arguments) {
            sb.append(argument.getName()).append(": ");

            String value = "";
            if (argument.getValue() instanceof String) {
                value = (String) argument.getValue();
                value = (value.trim().startsWith("$")) ? value : "\"" + value + "\"";
            } else {
                value = mapper.writeValueAsString(argument.getValue());
            }
            sb.append(value).append(" ");
        }
        return sb.toString();
    }

    @Data
    private class Argument {
        private final String name;
        private final Object value;

        Argument(final String name, final Object value) {
            this.name = name;
            this.value = value;
        }

    }
}
