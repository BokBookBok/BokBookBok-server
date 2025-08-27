package bokbookbok.server.infra;

import bokbookbok.server.infra.dto.ChatMsg;
import bokbookbok.server.infra.dto.OpenAIRequest;
import bokbookbok.server.infra.dto.OpenAIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAIClient {
    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public String question(String prompt) {
        ChatMsg userMsg = ChatMsg.builder()
                .role("user")
                .content(prompt)
                .build();

        OpenAIRequest request = OpenAIRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(userMsg))
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<OpenAIRequest> httpEntity = new HttpEntity<>(request, headers);

        log.info("GPT 요청 prompt:\n{}", prompt); // 요청 로그

        ResponseEntity<OpenAIResponse> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                httpEntity,
                OpenAIResponse.class
        );

        String result = response.getBody().getChoices().getFirst().message.getContent();
        log.info("GPT 응답 결과:\n{}", result); // 응답 로그
        return result;
    }
}
