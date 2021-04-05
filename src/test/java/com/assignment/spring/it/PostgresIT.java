package com.assignment.spring.it;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ContextConfiguration(initializers = PostgresIT.DockerPostgresDataSourceInitializer.class)
@Testcontainers
public abstract class PostgresIT {

  @Container
  private static final PostgreSQLContainer<?> POSTGRES_DB_CONTAINER = new PostgreSQLContainer<>(
      "postgres:9.1").withFileSystemBind("src/main/resources/sql/create/schema.sql",
      "/docker-entrypoint-initdb.d/init.sql", BindMode.READ_ONLY);


  public static class DockerPostgresDataSourceInitializer implements
      ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
      TestPropertySourceUtils.addInlinedPropertiesToEnvironment(applicationContext,
          "spring.datasource.url=" + POSTGRES_DB_CONTAINER.getJdbcUrl(),
          "spring.datasource.username=" + POSTGRES_DB_CONTAINER.getUsername(),
          "spring.datasource.password=" + POSTGRES_DB_CONTAINER.getPassword());
    }
  }
}
