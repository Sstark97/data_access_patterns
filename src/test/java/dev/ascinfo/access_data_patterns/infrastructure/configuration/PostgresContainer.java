package dev.ascinfo.access_data_patterns.infrastructure.configuration;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports.Binding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class PostgresContainer {
  @Value("${data.username}")
  private static String username;
  @Value("${data.password}")
  private static String password;

  @Bean
  @ServiceConnection
  PostgreSQLContainer<?> dataPostgresContainer() {
    return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
        .withExposedPorts(5432)
        .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                new HostConfig()
                    .withPortBindings(new PortBinding(Binding.bindPort(5432), new ExposedPort(5432))
                    )
            )
        )
        .withDatabaseName("data_patterns")
        .withUsername("user")
        .withPassword("password")
        .withInitScript("script/init.sql");
  }
}
