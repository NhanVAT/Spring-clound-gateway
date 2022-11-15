package evnict.gateway.filter;

import evnict.gateway.exception.JwtTokenMalformedException;
import evnict.gateway.exception.JwtTokenMissingException;
import evnict.gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {

  @Autowired
  private JwtUtil jwtUtil;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();

    //List các API không cần authen
    final List<String> apiEndpoints = new ArrayList<>();
    apiEndpoints.add("/register");
    apiEndpoints.add("/login");

    Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
        .noneMatch(uri -> r.getURI().getPath().contains(uri));

    if (isApiSecured.test(request)) {

      //1. Lấy JWT
      //1.1 Check nếu chưa có thì trả ve
      if (!request.getHeaders().containsKey("Authorization")) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        return response.setComplete();
      }

      //1.2 lấy JWT ra
      String bearerToken = request.getHeaders().getOrEmpty("Authorization").get(0);

      //1.3 xoa Bearer trong token
      if (bearerToken == null || bearerToken.split(" ").length < 2) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        return response.setComplete();
      }

      final String token = bearerToken.split(" ")[1];

      try {
        jwtUtil.validateToken(token);
      } catch (JwtTokenMalformedException | JwtTokenMissingException | NoSuchAlgorithmException |
               InvalidKeySpecException e) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.BAD_REQUEST);

        return response.setComplete();
      }

      Claims claims = jwtUtil.getClaims(token);
      exchange.getRequest().mutate().header("id", String.valueOf(claims.get("auth"))).build();
    }

    return chain.filter(exchange);
  }

}
