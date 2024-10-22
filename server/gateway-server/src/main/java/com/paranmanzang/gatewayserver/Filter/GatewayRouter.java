package com.paranmanzang.gatewayserver.Filter;

//게이터웨이 라우터 예제


import com.paranmanzang.gatewayserver.config.UriConfiguration;
import com.paranmanzang.gatewayserver.jwt.JWTUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Configuration
@RestController
@EnableConfigurationProperties(UriConfiguration.class)
public class GatewayRouter {

	private final UriConfiguration uriConfiguration;

	public GatewayRouter(UriConfiguration uriConfiguration) {
		this.uriConfiguration = uriConfiguration;
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder, JWTUtil jwtUtil) {
		return builder.routes()
				.route(p -> p.path("/get")
						.filters(f -> f.addRequestHeader("Hello", "World"))
						.uri("http://httpbin.org:80"))
				.route(r -> r.path("/api/chats/**")
						.uri("lb://chat-service"))
				.route(r -> r.path("/api/comments/**")
						.uri("lb://comment-service"))
				.route(r -> r.path("/api/groups/**")
						.uri("lb://group-service"))
				.route(r -> r.path("/api/files/**")
						.uri("lb://file-service"))
				.route(r -> r.path("/api/rooms/**")
						.uri("lb://room-service"))
				.route(r -> r.path("/api/users/**")
						.uri("lb://user-service"))
				.route(p -> p.host("*.circuitbreaker.com")
						.filters(f -> f.circuitBreaker(config -> config
								.setName("mycmd")
								.setFallbackUri("forward:/fallback")))
						.uri("http://httpbin.org:80"))
				.build();
	}

	@RequestMapping("/fallback")
	public Mono<String> fallback(Throwable e) {
		return Mono.just("fallback Method from GatewayRouter , error is " + e.getMessage());
	}
}




