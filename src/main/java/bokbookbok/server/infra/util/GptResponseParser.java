package bokbookbok.server.infra.util;

import bokbookbok.server.global.config.common.codes.ErrorCode;
import bokbookbok.server.global.config.exception.BusinessExceptionHandler;

public class GptResponseParser {

    public static ParsedVoteQuestion parse(String gptResponse) {
        String[] lines = gptResponse.split("\n");
        String question = null, option1 = null, option2 = null;

        for (String line : lines) {
            if (line.startsWith("질문:")) {
                question = line.replace("질문:", "").trim();
            } else if (line.startsWith("보기1:")) {
                option1 = line.replace("보기1:", "").trim();
            } else if (line.startsWith("보기2:")) {
                option2 = line.replace("보기2:", "").trim();
            }
        }

        if (question == null || option1 == null || option2 == null) {
            throw new BusinessExceptionHandler(ErrorCode.INVALID_GPT_RESPONSE);
        }

        return new ParsedVoteQuestion(question, option1, option2);
    }

    public record ParsedVoteQuestion(String question, String option1, String option2) {
    }

}
