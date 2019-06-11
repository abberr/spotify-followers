package se.albin.spotifyfollowers.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import se.albin.spotifyfollowers.model.Token;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;


@Service
public class TokenHandlerService {

    Logger logger = LoggerFactory.getLogger(TokenHandlerService.class);

    private static final long TOKEN_CHECK_INTERVAL_MS = 60 * 1000;
    private static final long TOKEN_EXPIRE_OFFSET_MS = 120 * 1000;

    @Value("${spotify.oauth2.client.clientId}")
    private String clientId;

    @Value("${spotify.oauth2.client.clientSecret}")
    private String clientSecret;

    @Value("${spotify.oauth2.client.accessTokenUri}")
    private String tokenUri;

    @Value("${spotify.oauth2.client.grantType}")
    private String grantType;

    AtomicReference<Token> token = new AtomicReference();

    @PostConstruct
    private void init() {
        token.set(retrieveTokenFromServer());
    }

    public Token getToken() {
        return token.get();
    }

    @Scheduled(fixedRate = TOKEN_CHECK_INTERVAL_MS)
    private void scheduledUpdateToken() {
        long now = Instant.now().toEpochMilli();
        long expireDate = token.get().getExpireDate();
        long timeToLive = expireDate - now;

        if (timeToLive < TOKEN_EXPIRE_OFFSET_MS) {
            logger.info("Access token epires in {} ms. Retrieving new token", timeToLive);
            token.set(retrieveTokenFromServer());
        }
    }

    private Token retrieveTokenFromServer() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(clientId, clientSecret));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", grantType);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Token> response = restTemplate.postForEntity(tokenUri, request, Token.class);

        return response.getBody();
    }
}
