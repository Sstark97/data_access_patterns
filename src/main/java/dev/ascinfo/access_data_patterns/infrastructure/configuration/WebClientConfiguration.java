package dev.ascinfo.access_data_patterns.infrastructure.configuration;

import java.time.Duration;

import javax.net.ssl.SSLException;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Configuration
public class WebClientConfiguration implements WebClientCustomizer {

  @Value("${webclient.use-insecure-trust-manager:false}")
  private boolean useInsecureTrustManager;
  @Value("${reactor.netty.pool.maxConnections:500}")
  private int maxConnections;
  @Value("${reactor.netty.pool.maxIdleTime:-1}")
  private Long maxIdleTime;
  @Value("${reactor.netty.pool.maxLifeTime:-1}")
  private Long maxLifeTime;
  @Value("${reactor.netty.pool.acquireTimeout:45000}")
  private Long acquireTimeout;

  @Override
  public void customize(Builder webClientBuilder) {
    ConnectionProvider connectionProvider = ConnectionProvider.builder("access-data-patterns")
        .maxConnections(maxConnections)
        .maxIdleTime(Duration.ofMillis(maxIdleTime))
        .maxLifeTime(Duration.ofMillis(maxLifeTime))
        .pendingAcquireTimeout(Duration.ofMillis(acquireTimeout))
        .build();
    HttpClient httpClient = HttpClient.create(connectionProvider);
    webClientBuilder
        .clientConnector(new ReactorClientHttpConnector(configureTrustManager(httpClient)));
  }

  private HttpClient configureTrustManager(final HttpClient httpClient) {
    if (useInsecureTrustManager) {
      try {
        final SslContext sslContext = SslContextBuilder.forClient().trustManager(
            InsecureTrustManagerFactory.INSTANCE).build();
        return httpClient.secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));
      } catch (SSLException e) {
        // log.warn("Cannot configure insecure trust manager", e);
      }
    }
    return httpClient;
  }
}