package com.arogut.homex.gateway.client;

import com.arogut.homex.gateway.JwtUtil;
import com.arogut.homex.gateway.model.AuthType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactivefeign.client.ReactiveHttpRequest;
import reactivefeign.client.ReactiveHttpRequestInterceptor;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FeignClientJwtInterceptor implements ReactiveHttpRequestInterceptor {

    private final JwtUtil jwtUtil;
    private String token;

    @PostConstruct
    public void setUp() {
        updateToken();
    }

    @Override
    public ReactiveHttpRequest apply(ReactiveHttpRequest reactiveHttpRequest) {
        if(jwtUtil.isTokenExpired(token)) {

        }
        reactiveHttpRequest.headers().put("Authorization", List.of("Bearer " + token));
        return reactiveHttpRequest;
    }

    private void updateToken() {
        this.token = jwtUtil.generateToken("gateway", Map.of("role", AuthType.INTERNAL));
    }
}
