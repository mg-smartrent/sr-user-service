//package com.mg.samartrent.user.integration.resource
//
//
//import com.mg.samartrent.user.integration.SetupITest
//import com.mg.smartrent.domain.models.User
//import com.mg.smartrent.user.Application
//import com.mg.smartrent.user.generated.UserServiceGQLSchema
//import com.mg.smartrent.user.graphql.GQLArguments
//import com.mg.smartrent.user.graphql.GQLFragment
//import com.mg.smartrent.user.graphql.GQLQuery
//import com.mg.smartrent.user.graphql.GQLVariables
//import com.mg.smartrent.user.service.UserService
//import org.junit.Ignore
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.boot.web.server.LocalServerPort
//import org.springframework.http.HttpStatus
//import org.springframework.web.client.RestTemplate
//
//import static com.mg.samartrent.user.ModelBuilder.generateUser
//import static com.mg.smartrent.domain.models.BizItem.Fields.*
//import static com.mg.smartrent.user.graphql.GQLQuery.Type.Mutation
//
//@SpringBootTest(
//        classes = Application.class,
//        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
//        properties = ["eureka.client.enabled:false"]
//)
//class TestUserRestControllerGraph extends SetupITest {
//
//    @LocalServerPort
//    private int port;
//    @Autowired
//    private UserService userService
//    @Autowired
//    private RestTemplate restTemplate;
//    static String restURL
//
//    def setup() {
//        purgeCollection(User.class)
//        restURL = "http://localhost:$port/graphql"
//    }
//
//    @Ignore
//    def "test: get user by ID"() {
//        setup:
//        UserServiceGQLSchema.QueryFindByIdArgs
//
//
//
//        def user = userService.create(generateUser())
//        GQLQuery createUserQuery = new GQLQuery(Mutation, "create",
//                new GQLArguments().add("user", '$user'),
//                new GQLVariables().add("user", user),
//                new GQLFragment()
//                        .add(id)
//                        .add(createdDate)
//                        .add(modifiedDate)
//                        .add(User.Fields.firstName)
//                        .add(User.Fields.lastName)
//                        .add(User.Fields.gender)
//                        .add(User.Fields.dateOfBirth)
//                        .add(User.Fields.email)
//                        .add(User.Fields.status))
//        when:
//        def responseEntity = restTemplate.postForEntity(restURL, createUserQuery, Object.class)
//
//        then:
//        responseEntity.getStatusCode() == HttpStatus.OK
//    }
//
//    def "test: get user by email"() {
//        setup:
//        def user = userService.create(generateUser())
//
//        ObjectNode variables = new ObjectMapper().createObjectNode();
//        variables.put(User.Fields.email, user.email);
//
//        when:
//        GraphQLResponse response = graphQLTestTemplate.perform("graphql/user_query_findByEmail.graphql", variables);
//        String jPath = '$.data.findByEmail'
//
//        then:
//        response != null
//        response.isOk()
//        def context = response.context()
//        context.read("${jPath}.$id") != null
//        context.read("${jPath}.$createdDate") != null
//        context.read("${jPath}.$modifiedDate") != null
//        context.read("${jPath}.$User.Fields.firstName") == user.firstName
//        context.read("${jPath}.$User.Fields.lastName") == user.lastName
//        context.read("${jPath}.$User.Fields.status") == EnUserStatus.Pending.name()
//        context.read("${jPath}.$User.Fields.dateOfBirth") != null
//        context.read("${jPath}.$User.Fields.gender") == user.gender.name()
//        context.read("${jPath}.$User.Fields.email") == user.email
//        context.read("${jPath}.$User.Fields.enabled") == user.enabled
//        context.read("${jPath}.[?(@.$User.Fields.password)]") == []
//        context.read("${jPath}.length()") == 10
//    }
//
//    def "test: create user"() {
//        setup:
//        def userInput = generateUserInput(null)
//        ObjectNode variables = new ObjectMapper().createObjectNode();
//        variables.putPOJO("user", userInput)
//
//        when:
//        GraphQLResponse response = graphQLTestTemplate.perform("graphql/user_mutation_create.graphql", variables);
//        String jPath = '$.data.create'
//
//        then:
//        response != null
//
//        response.isOk()
//        def context = response.context()
//        context.read("${jPath}.$id") != null
//        context.read("${jPath}.$createdDate") != null
//        context.read("${jPath}.$modifiedDate") != null
//        context.read("${jPath}.$User.Fields.dateOfBirth") != null
//        context.read("${jPath}.$User.Fields.firstName") == userInput.firstName
//        context.read("${jPath}.$User.Fields.lastName") == userInput.lastName
//        context.read("${jPath}.$User.Fields.status") == EnUserStatus.Pending.name()
//        context.read("${jPath}.$User.Fields.gender") == userInput.gender
//        context.read("${jPath}.$User.Fields.email") == userInput.email
//        context.read("${jPath}.$User.Fields.enabled") == false
//        context.read("${jPath}.[?(@.$User.Fields.password)]") == []
//        context.read("${jPath}.length()") == 10
//    }
//
//    def "test: update user"() {
//        setup:
//        def dbUser = userService.create(generateUser())
//        def userInput = generateUserInput(dbUser)
//        userInput.firstName = "UpdatedFromGraphQLTest"
//
//        ObjectNode variables = new ObjectMapper().createObjectNode();
//        variables.putPOJO("user", userInput)
//
//        when:
//        GraphQLResponse response = graphQLTestTemplate.perform("graphql/user_mutation_update.graphql", variables);
//        String jPath = '$.data.update'
//
//        then:
//        response != null
//        response.isOk()
//        def context = response.context()
//        context.read("${jPath}.$User.Fields.firstName") == "UpdatedFromGraphQLTest"
//        context.read("${jPath}.length()") == 10
//    }
//
//    def "test: enable user account"() {
//        setup:
//        def dbUser = userService.create(generateUser())
//
//        ObjectNode variables = new ObjectMapper().createObjectNode();
//        variables.put(id, dbUser.getId());
//        variables.put(User.Fields.enabled, true);
//
//        when:
//        GraphQLResponse response = graphQLTestTemplate.perform("graphql/user_mutation_enable.graphql", variables);
//        String jPath = '$.data.enable'
//
//        then:
//        response != null
//        response.isOk()
//        def context = response.context()
//        context.read("${jPath}.$User.Fields.enabled") == true
//        context.read("${jPath}.length()") == 10
//    }
//
//    def "test: reset user password"() {
//        setup:
//        def user = userService.create(generateUser())
//
//        ObjectNode variables = new ObjectMapper().createObjectNode();
//        variables.put(id, user.id);
//        variables.put("rawPassword", "abcabc1234");
//
//        when:
//        GraphQLResponse response = graphQLTestTemplate.perform("graphql/user_mutation_resetPassword.graphql", variables);
//        String jPath = '$.data.resetPassword'
//
//        then:
//        response != null
//        response.isOk()
//        def context = response.context()
//        context.read("${jPath}") == true
//    }
//
//
//}
//
//
