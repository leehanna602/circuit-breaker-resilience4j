package com.hannah.resilience4j.support.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "유효하지 않은 입력입니다."),
    PROMPT_CONFIG_ERROR(HttpStatus.BAD_REQUEST, "프롬프트 설정 확인해주세요."),

    REQUEST_TIMEOUT(HttpStatus.REQUEST_TIMEOUT, "요청 시간이 초과되었습니다."),

    EXTERNAL_LLM_API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "LLM 호출 오류"),
    ;

    private final HttpStatus status;
    private final String message;
}