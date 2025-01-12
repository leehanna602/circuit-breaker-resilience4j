//package com.hannah.resilience4j.support.circuit;
//
//import feign.FeignException;
//import feign.RetryableException;
//
//import java.util.concurrent.TimeoutException;
//import java.util.function.Predicate;
//
//public class DefaultExceptionRecordFailurePredicate implements Predicate<Throwable> {
//
//    /* recordFailurePredicate는 어떤 예외를 Fail로 기록할 것인지 결정하기 위한 Predicate 설정
//    * - true를 반환하면 요청 실패로 기록
//    * - 실패가 쌓이면 서킷이 OPEN 상태로 변경됨 */
//    @Override
//    public boolean test(Throwable t) {
//        // occurs in @CircuitBreaker TimeLimiter
//        if (t instanceof TimeoutException) {
//            return true;
//        }
//
//        // occurs in @OpenFein
//        if (t instanceof RetryableException) {
//            return true;
//        }
//
//        // 4xx 에러도 포함
//        if (t instanceof FeignException.FeignClientException) {
//            return true;
//        }
//
//        return t instanceof FeignException.FeignServerException;
//    }
//
//}
