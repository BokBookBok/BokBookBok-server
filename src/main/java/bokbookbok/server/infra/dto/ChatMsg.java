package bokbookbok.server.infra.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ChatMsg {
    private String role;
    private String content;
}
