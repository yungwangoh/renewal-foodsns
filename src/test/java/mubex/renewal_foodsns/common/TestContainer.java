package mubex.renewal_foodsns.common;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestContainer {

    private static final String MYSQL_VERSION = "mysql:8.2.0";
//    private static final String ES = "docker.elastic.co/elasticsearch/elasticsearch:7.17.23";

    @Bean
    @ServiceConnection
    public MySQLContainer<?> mySqlContainer() {
        MySQLContainer<?> mySQLContainer = new MySQLContainer<>(MYSQL_VERSION);

        return mySQLContainer.withInitScript("init.sql");
    }

//    @Bean
//    @ServiceConnection
//    @DependsOn("mySqlContainer")
//    public ElasticsearchContainer elasticsearchContainer() {
//        ElasticsearchContainer elasticsearchContainer = new ElasticsearchContainer(ES);
//
//        return elasticsearchContainer
//                .withCommand("bin/elasticsearch-plugin install analysis-nori");
//    }
}
