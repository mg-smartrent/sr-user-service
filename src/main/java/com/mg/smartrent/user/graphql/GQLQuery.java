package com.mg.smartrent.user.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.RawValue;


public class GQLQuery {
    private final ObjectMapper mapper = new ObjectMapper();
    private Type type;
    private String functionName;
    private GQLArguments arguments;
    private GQLFragment fragment;
    private GQLVariables variables;

    public GQLQuery(final Type type,
                    final String functionName,
                    final GQLArguments arguments,
                    final GQLVariables variables,
                    final GQLFragment fragment) {
        this.type = type;
        this.functionName = functionName;
        this.arguments = arguments;
        this.variables = variables;
        this.fragment = fragment;
    }

    public String toString() {
        StringBuilder query = new StringBuilder();
        query.append("{ ").append(type.name).append(" ");
        query.append(functionName).append("( ").append(arguments.toString()).append(" )");
        query.append("{ ").append(fragment.toString()).append(" }");


        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.putRawValue("operationName", null);
        rootNode.putRawValue("query", new RawValue(query.toString()));
        rootNode.putRawValue("variables", new RawValue(variables.toString()));

        return rootNode.toPrettyString();
    }

    enum Type {
        Query("query"),
        Mutation("mutation");

        private final String name;

        Type(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
