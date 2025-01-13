package com.hannah.resilience4j.llm.domain;

import lombok.Data;

@Data
public class PromptDictionary {
    private String system;
    private Parameters parameters;
    private Models models;
    private String description;

    @Data
    public static class Parameters {
        private float temperature;
        private float topP;
    }

    @Data
    public static class Models {
        private String primary;
        private String fallback;
    }

}
