package com.hannah.resilience4j.circuitBreakerBasic.controller;

import com.hannah.resilience4j.circuitBreakerBasic.service.CircuitBreakerTestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test/circuit")
@Tag(name = "Test")
public class CircuitBreakerTestController {

    private final CircuitBreakerTestService circuitBreakerTestService;

    @PostMapping("/exception")
    public ResponseEntity<Object> circuitBreakerTest(@RequestParam int number) {
        Object result = circuitBreakerTestService.circuitBreaker(number);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/timeout")
    public ResponseEntity<Object> circuitBreakerTimeoutTest() throws Exception {
        Object result = circuitBreakerTestService.circuitBreakerTimeout().get();
        return ResponseEntity.ok().body(result);
    }

}
