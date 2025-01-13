package com.hannah.resilience4j.llm.infrastructure.openAi;

import com.hannah.resilience4j.llm.infrastructure.openAi.dto.OpenAiRes;
import com.hannah.resilience4j.llm.infrastructure.openAi.dto.OpenAiReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "chat-gpt-client", url = "https://api.openai.com/v1",
        configuration = {ChatHeaderFeignConfiguration.class}
)
public interface OpenAiApi {

    @PostMapping(path = "/chat/completions")
    OpenAiRes openAiCompletions(@RequestBody OpenAiReq req);

}