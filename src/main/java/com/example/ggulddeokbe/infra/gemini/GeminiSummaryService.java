package com.example.ggulddeokbe.infra.gemini;

import com.example.ggulddeokbe.domain.policy.dto.PolicyDetailResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class GeminiSummaryService {

    private static final String GROQ_URL = "https://api.groq.com/openai/v1/chat/completions";

    private final RestClient restClient;
    private final String model;

    public GeminiSummaryService(
            @Value("${groq.api-key}") String apiKey,
            @Value("${groq.model}") String model
    ) {
        this.model = model;
        this.restClient = RestClient.builder()
                .baseUrl(GROQ_URL)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }

    public String generateDescription(PolicyDetailResponse policy) {
        String prompt = buildPrompt(policy);

        try {
            Map<?, ?> body = Map.of(
                    "model", model,
                    "messages", List.of(
                            Map.of("role", "user", "content", prompt)
                    )
            );

            Map<?, ?> response = restClient.post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .body(Map.class);

            return extractText(response);
        } catch (Exception e) {
            log.warn("Groq API 호출 실패 (plcyNo={}): {}", policy.plcyNo(), e.getMessage());
            return null;
        }
    }

    private String buildPrompt(PolicyDetailResponse policy) {
        String raw = String.join(" ",
                nullToEmpty(policy.policyName()),
                nullToEmpty(policy.description()),
                nullToEmpty(policy.supportContent()),
                nullToEmpty(policy.requiredDocuments()),
                nullToEmpty(policy.organizationName())
        ).trim();

        return String.format("""
                다음은 청년 정책 원문 정보입니다.

                %s

                위 내용을 청년들이 쉽게 이해할 수 있도록 한국어로 2~3문장으로 자연스럽게 요약해주세요.
                요약문만 출력하고, 별도의 제목이나 부가 설명은 붙이지 마세요.
                """, raw);
    }

    @SuppressWarnings("unchecked")
    private String extractText(Map<?, ?> response) {
        if (response == null) return null;
        List<?> choices = (List<?>) response.get("choices");
        if (choices == null || choices.isEmpty()) return null;
        Map<?, ?> message = (Map<?, ?>) ((Map<?, ?>) choices.get(0)).get("message");
        if (message == null) return null;
        Object content = message.get("content");
        return content != null ? content.toString().trim() : null;
    }

    private String nullToEmpty(String value) {
        return value != null ? value : "";
    }
}
