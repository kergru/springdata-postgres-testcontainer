package com.kgr.springdatapostgrestestcontainer.repository;

import com.kgr.springdatapostgrestestcontainer.entity.Animal;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = {AnimalRepositoryIT.Initializer.class})
@Testcontainers
class AnimalRepositoryIT {

    @Container
    private static JdbcDatabaseContainer postgreSQLContainer = new PostgreSQLContainer()
            .withDatabaseName("xyz")
            .withInitScript("it/init.sql")
            .withUsername("postgres")
            .withPassword("postgres");

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
    @Autowired
    private AnimalRepository repository;

    @Test
    void test_save_animal() {
        Animal animal = new Animal();
        animal.setName("Lion");

        Animal actual = repository.save(animal);

        assertNotNull(actual);
        assertNotNull(actual.getId());

        Optional<Animal> actual2 = repository.findById(actual.getId());

        assertTrue(actual2.isPresent());
    }
}