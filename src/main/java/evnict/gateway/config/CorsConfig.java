package evnict.gateway.config;

import io.netty.handler.logging.LogLevel;
import java.util.Arrays;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

//@Configuration
public class CorsConfig extends CorsConfiguration {

  //  @Bean
  HttpClient httpClient() {
    return HttpClient.create()
        .wiretap("LoggingFilter", LogLevel.INFO, AdvancedByteBufFormat.TEXTUAL);
  }

  //  @Bean
  public CorsWebFilter corsFilter() {
    org.springframework.web.cors.CorsConfiguration corsConfiguration = new org.springframework.web.cors.CorsConfiguration();

    corsConfiguration.setAllowCredentials(false);
    corsConfiguration.addAllowedOrigin("*");
    corsConfiguration.setAllowedMethods(
        Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
    corsConfiguration.addAllowedHeader("origin");
    corsConfiguration.addAllowedHeader("content-type");
    corsConfiguration.addAllowedHeader("accept");
    corsConfiguration.addAllowedHeader("authorization");
    corsConfiguration.addAllowedHeader("cookie");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);

    return new CorsWebFilter(source);
  }
}
