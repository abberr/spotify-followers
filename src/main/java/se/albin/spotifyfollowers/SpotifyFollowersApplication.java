package se.albin.spotifyfollowers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import se.albin.spotifyfollowers.security.Oauth2Interceptor;

@SpringBootApplication
public class SpotifyFollowersApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotifyFollowersApplication.class, args);
	}

	@Autowired
    Oauth2Interceptor oauth2Interceptor;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder
				.additionalInterceptors(oauth2Interceptor)
				.build();
	}
}
