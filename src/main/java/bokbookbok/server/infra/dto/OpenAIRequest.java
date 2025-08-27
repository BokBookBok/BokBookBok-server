package bokbookbok.server.infra.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class OpenAIRequest {
    private String model = "gpt-3.5-turbo";
    private List<ChatMsg> messages;
}
