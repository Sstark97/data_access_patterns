package dev.ascinfo.access_data_patterns.infrastructure.external;

import java.util.List;

import dev.ascinfo.access_data_patterns.infrastructure.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UserClient {

  private final WebClient webClient;

  public UserClient(
      @Value("${data.api.url}") String baseUrl,
      WebClient.Builder webClientBuilder
  ) {
    this.webClient = webClientBuilder
        .baseUrl(baseUrl)
        .build();
  }

  public List<UserDto> retrieveUsers() {
    return webClient.get()
        .uri("/users")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToFlux(UserDto.class)
        .collectList()
        .block();
  }
}