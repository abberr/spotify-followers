package se.albin.spotifyfollowers.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import se.albin.spotifyfollowers.service.TokenHandlerService;

import java.io.IOException;

@Component
public class Oauth2Interceptor implements ClientHttpRequestInterceptor {

    @Autowired
    TokenHandlerService tokenHandlerService;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] bytes, ClientHttpRequestExecution execution) throws IOException {
        String accessToken = tokenHandlerService.getToken().getAccess_token();
        request.getHeaders().add("Authorization", "Bearer " + accessToken);
        return execution.execute(request, bytes);
    }
}
