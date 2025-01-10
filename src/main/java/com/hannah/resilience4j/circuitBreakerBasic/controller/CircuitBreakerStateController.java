package com.hannah.resilience4j.circuitBreakerBasic.controller;

import com.hannah.resilience4j.circuitBreakerBasic.service.CircuitBreakerStateService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test/state")
@Tag(name = "Test State")
public class CircuitBreakerStateController {

    private final CircuitBreakerStateService circuitBreakerStateService;

    /* circuit OPEN */
    @PostMapping("/circuit/open")
    public ResponseEntity<CircuitBreaker.State> circuitBreakerOpen(@RequestParam(defaultValue = "default") String name) {
        CircuitBreaker.State state = circuitBreakerStateService.circuitBreakerOpen(name);
        return ResponseEntity.ok().body(state);
    }

    /* circuit CLOSED */
    @PostMapping("/circuit/close")
    public ResponseEntity<CircuitBreaker.State> circuitBreakerClose(@RequestParam(defaultValue = "default") String name) {
        CircuitBreaker.State state = circuitBreakerStateService.circuitBreakerClose(name);
        return ResponseEntity.ok().body(state);
    }

    /* circuit HALF_OPEN */
    @PostMapping("/circuit/half-open")
    public ResponseEntity<CircuitBreaker.State> circuitBreakerHalfOpen(@RequestParam(defaultValue = "default") String name) {
        CircuitBreaker.State state = circuitBreakerStateService.circuitBreakerHalfOpen(name);
        return ResponseEntity.ok().body(state);
    }

    /* circuit State */
    @PostMapping("/circuit/status")
    public ResponseEntity<CircuitBreaker.State> circuitBreakerState(@RequestParam(defaultValue = "default") String name) {
        CircuitBreaker.State state = circuitBreakerStateService.circuitBreakerState(name);
        return ResponseEntity.ok().body(state);
    }

    /* all circuit */
    @PostMapping("/circuit/all")
    public ResponseEntity<Map<String, CircuitBreaker.State>> circuitBreakerAll() {
        Map<String, CircuitBreaker.State> result = circuitBreakerStateService.circuitBreakerAll();
        return ResponseEntity.ok().body(result);
    }

}
