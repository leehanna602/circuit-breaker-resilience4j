package com.hannah.resilience4j.llm.infrastructure.gemini;

import com.hannah.resilience4j.llm.infrastructure.gemini.dto.GeminiAiReq;
import com.hannah.resilience4j.llm.infrastructure.gemini.dto.GeminiAiRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "gemini-client", url = "https://generativelanguage.googleapis.com/v1/models",
        configuration = {GeminiConfiguration.class}
)
public interface GeminiAiApi {

    @PostMapping(path = "/{model}:generateContent")
    GeminiAiRes generateContent(@PathVariable String model, @RequestBody GeminiAiReq geminiAiReq);

}
