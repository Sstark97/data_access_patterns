package dev.ascinfo.access_data_patterns;

import dev.ascinfo.access_data_patterns.infrastructure.configuration.PostgresContainer;
import org.springframework.boot.SpringApplication;

public class TestAccessDataPatternsApplication {

  public static void main(String[] args) {
    SpringApplication.from(AccessDataPatternsApplication::main)
        .with(PostgresContainer.class).run(args);
  }

}
