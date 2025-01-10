package com.hannah.resilience4j.circuitBreakerBasic.service;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.collection.Seq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
@RequiredArgsConstructor
public class CircuitBreakerStateService {

    private final CircuitBreakerRegistry circuitBreakerRegistry;

    public CircuitBreaker.State circuitBreakerOpen(String name) {
        circuitBreakerRegistry.circuitBreaker(name).transitionToOpenState();
        return circuitBreakerRegistry.circuitBreaker(name).getState();
    }

    public CircuitBreaker.State circuitBreakerClose(String name) {
        circuitBreakerRegistry.circuitBreaker(name).transitionToClosedState();
        return circuitBreakerRegistry.circuitBreaker(name).getState();
    }

    public CircuitBreaker.State circuitBreakerHalfOpen(String name) {
        circuitBreakerRegistry.circuitBreaker(name).transitionToHalfOpenState();
        return circuitBreakerRegistry.circuitBreaker(name).getState();
    }

    public CircuitBreaker.State circuitBreakerState(String name) {
        return circuitBreakerRegistry.circuitBreaker(name).getState();
    }

    public Map<String, CircuitBreaker.State> circuitBreakerAll() {
        Seq<CircuitBreaker> circuitBreakers = circuitBreakerRegistry.getAllCircuitBreakers();
        Map<String, CircuitBreaker.State> result = new HashMap<>();
        for (CircuitBreaker circuitBreaker : circuitBreakers) {
            result.put(circuitBreaker.getName(), circuitBreaker.getState());
        }
        return result;
    }

}
