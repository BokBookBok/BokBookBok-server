package bokbookbok.server.infra.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class OpenAIResponse {
    public List<Choice> choices;

    public static class Choice {
        public ChatMsg message;
    }
}
