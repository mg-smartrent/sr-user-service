package com.mg.samartrent.user.unit

import com.mg.smartrent.domain.enums.EnUserStatus
import com.mg.smartrent.domain.models.User
import com.mg.smartrent.user.graphql.GQLArguments
import com.mg.smartrent.user.graphql.GQLFragment
import com.mg.smartrent.user.graphql.GQLQuery
import com.mg.smartrent.user.graphql.GQLVariables
import spock.lang.Specification

import static com.mg.smartrent.user.graphql.GQLQuery.Type.Query

class GraphQLQueryBuilderTest extends Specification {

    def "test: GQL query builder"() {

        when:
        GQLQuery query = new GQLQuery(
                Query,
                "usersByName",
                new GQLArguments()
                        .add("firstName", "Andrei")
                        .add("magicNumber", 1)
                        .add("flag", true)
                        .add("enum", EnUserStatus.Active)
                        .add("lastName", "\$var1")
                        .add("middleName", "\$var2")
                        .add("lastName", "{test:\"testVal\"}"),
                new GQLVariables()
                        .add("var1", "varValue1")
                        .add("var2", new User()),
                new GQLFragment()
                        .add("1")
                        .add("2")
                        .add("A",
                                new GQLFragment()
                                        .add("A1")
                                        .add("A2")
                                        .add("B",
                                                new GQLFragment()
                                                        .add("B1")
                                                        .add("B2")
                                        )
                        )
        )
        then:
        query.toString() != null
    }
}
