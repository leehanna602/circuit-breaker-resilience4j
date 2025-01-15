## 🌿 Resilience4j를 통한 Circuit Breaker 패턴
> Resilience4j 라이브러리 사용 예시 코드입니다.

### ✅ Resilience4j란?
Resilience4j는 Java 애플리케이션의 fault tolerance(장애 허용)를 위한 경량 라이브러리이다. Netflix Hystrix에서 영감을 받아 제작되었으며, 함수형 프로그래밍 패러다임을 기반으로 설계되었다. Netflix가 만든 Hystrix는 지원을 중단해 Resilience4J를 사용하면 된다. Resilience4j는 Spring Boot 2 와 Spring Boot 3에 따라 사용할 수 있는 버전이 다르니 필요에 맞게 사용하면 된다.

### ✅ 핵심 모듈
Resilience4j는 모듈화된 구조로 필요한 기능만 선택해 사용 가능하다. 제공하는 주요 모듈은 다음과 같다.
- resilience4j-circuitbreaker: 장애 감지 및 차단을 위한 Circuit Breaker 패턴 구현
- resilience4j-ratelimiter: 서비스 요청에 대한 속도 제한을 구현
- resilience4j-bulkhead: 동시 실행되는 요청 수를 제한하여 시스템 리소스를 보호 (동시성 제어)
- resilience4j-retry: 재시도 메커니즘
- resilience4j-timelimiter: 요청 처리 시간을 제한하여 타임아웃을 관리

### ✅ Circuit Breaker 상태 확인
CircuitBreaker 상태 모니터링을 위해 actuator 설정을 추가할 수 있다. 이 설정을 하면 http://localhost:8080/actuator/health 를 통해 Circuit Breaker 상태를 확인할 수 있다.

### ✅ Circuit Breaker 적용 예제
1. circuitBreakerBasic 패키지: 간단한 사용 예시와 상태 확인 및 변경 예제
2. llm 패키지: OpenAI API를 통한 LLM 답변 생성 기능에 Circuit Breaker 패턴을 적용한 예제
    * 참고: [SequenceDiagram.md](docs/SequenceDiagram.md)
