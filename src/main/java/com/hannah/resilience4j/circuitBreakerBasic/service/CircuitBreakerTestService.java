package com.hannah.resilience4j.circuitBreakerBasic.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;


@Service
@Slf4j
@RequiredArgsConstructor
public class CircuitBreakerTestService {


    @CircuitBreaker(name = "default", fallbackMethod = "circuitBreakerExceptionFallback")
    public Object circuitBreaker(int number){
        if(number == 1){
            throw new RuntimeException();
        }
        return "Success";
    }

    public Object circuitBreakerExceptionFallback(Exception e) {
        log.info("=====> circuitBreakerExceptionFallback");
        return "circuitBreakerExceptionFallback";
    }

    @CircuitBreaker(name = "default", fallbackMethod = "circuitBreakerTimeoutFallback")
    @TimeLimiter(name = "default")
    public CompletableFuture<Object> circuitBreakerTimeout(){
        return CompletableFuture.supplyAsync(() -> {
            try {
                // 설정한 timeoutDuration 이상 걸릴 경우 테스트
                Thread.sleep(7000);
                return "Success";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<String> circuitBreakerTimeoutFallback(Exception e) {
        log.info("=====> circuitBreakerTimeoutFallback");
        return CompletableFuture.completedFuture("circuitBreakerTimeoutFallback");
    }


}
