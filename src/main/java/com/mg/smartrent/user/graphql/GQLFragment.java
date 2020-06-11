package com.mg.smartrent.user.graphql;

import java.util.HashMap;

public class GQLFragment {
    private String fieldName;
    private GQLFragment fragment;
    private HashMap<String, GQLFragment> mapOfFields = new HashMap<>();

    public GQLFragment add(final String fieldName) {
        mapOfFields.put(fieldName, null);
        return this;
    }

    public GQLFragment add(final String fieldName, final GQLFragment fragment) {
        mapOfFields.put(fieldName, fragment);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        mapOfFields.forEach((fieldName, fragment) -> {
            sb.append(fieldName).append(" ");
            if (fragment != null) {
                sb.append("{ ").append(fragment.toString()).append(" } ");
            }
        });
        return sb.toString();
    }

}
