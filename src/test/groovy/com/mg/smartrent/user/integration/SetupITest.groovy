package com.mg.smartrent.user.integration
//package com.mg.samartrent.user.integration
//
//import com.fasterxml.jackson.databind.JsonNode
//import com.fasterxml.jackson.databind.ObjectMapper
//import de.flapdoodle.embed.mongo.MongodExecutable
//import de.flapdoodle.embed.mongo.MongodStarter
//import de.flapdoodle.embed.mongo.config.IMongodConfig
//import de.flapdoodle.embed.mongo.config.MongodConfigBuilder
//import de.flapdoodle.embed.mongo.config.Net
//import de.flapdoodle.embed.mongo.distribution.Version
//import de.flapdoodle.embed.process.runtime.Network
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.data.mongodb.core.MongoTemplate
//import org.springframework.data.mongodb.core.query.Criteria
//import org.springframework.data.mongodb.core.query.Query
//import org.springframework.http.MediaType
//import org.springframework.test.context.TestPropertySource
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.MvcResult
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
//import spock.lang.Specification
//
//@TestPropertySource(locations = "classpath:application.yml")
//class SetupITest extends Specification {
//    protected static MockMvc mockMvc
//    private static MongodExecutable mongoExecutable = null
//
//    @Autowired
//    private MongoTemplate mongoTemplate
//
//
//    def setupSpec() {
//        startEmbeddedMongo()
//    }
//
//    def cleanupSpec() {
//        stopEmbeddedMongo()
//    }
//
//    void purgeCollection(Class entityClazz) {
//        mongoTemplate.remove(new Query(Criteria.where("_id").exists(true)), entityClazz)
//        println("Collection ${entityClazz.simpleName} purged.")
//    }
//
//
//    MvcResult doPost(MockMvc mockMvc, String restUri, Object model) {
//        if (!(model instanceof String)) {
//            model = new ObjectMapper().writeValueAsString(model)
//        }
//
//        return mockMvc
//                .perform(MockMvcRequestBuilders.post(restUri)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content((String) model))
//                .andReturn()
//    }
//
//
//    MvcResult doPut(MockMvc mockMvc, String restUri, Object model) {
//        return mockMvc
//                .perform(MockMvcRequestBuilders.put(restUri)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(model)))
//                .andReturn()
//    }
//
//    MvcResult doPost(MockMvc mockMvc, String restUri, List models) {
//        return mockMvc
//                .perform(MockMvcRequestBuilders.post(restUri)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(models)))
//                .andReturn()
//    }
//
//
//    MvcResult doGet(MockMvc mockMvc, String restUri) {
//        return mockMvc.perform(MockMvcRequestBuilders.get(restUri)).andReturn()
//    }
//
//    Object mvcResultToModel(MvcResult mvcResult, Class modelClass) {
//        return new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), modelClass)
//    }
//
//    JsonNode mvcResultToJsonNode(MvcResult result) {
//        return new ObjectMapper().readTree(result.getResponse().getContentAsString())
//    }
//
//    String readGraphQLResult(MvcResult result, String... pathFields) {
//        def node = mvcResultToJsonNode(result)
//        for (String field : pathFields) {
//            node = node.path(field)
//        }
//
//        return node.toString()
//    }
//
//    Object readGraphQLResult(MvcResult result, Class modelClass, String... pathFields) {
//        String model = readGraphQLResult(result, modelClass, pathFields)
//        return new ObjectMapper().readValue(model, modelClass)
//    }
//
//
//    private static void startEmbeddedMongo() {
//        MongodStarter starter = MongodStarter.getDefaultInstance()
//        String bindIp = "localhost"
//        int port = 12345
//        IMongodConfig mongoConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION).net(new Net(bindIp, port, Network.localhostIsIPv6())).build()
//
//        mongoExecutable = null
//        try {
//            mongoExecutable = starter.prepare(mongoConfig)
//            mongoExecutable.start()
//        } catch (Exception ignore) {
//            if (mongoExecutable != null)
//                mongoExecutable.stop()
//        }
//
//    }
//
//    private static void stopEmbeddedMongo() {
//        if (mongoExecutable != null) {
//            mongoExecutable.stop()
//        }
//    }
//}
