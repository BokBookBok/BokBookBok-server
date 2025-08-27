package bokbookbok.server.domain.book.application;

import bokbookbok.server.domain.book.domain.Vote;
import bokbookbok.server.domain.book.domain.VoteUser;
import bokbookbok.server.domain.book.domain.enums.VoteOption;
import bokbookbok.server.domain.book.dto.response.VoteResultItem;
import bokbookbok.server.domain.book.dto.response.VoteResultResponse;
import bokbookbok.server.domain.book.repository.BookRepository;
import bokbookbok.server.domain.book.repository.VoteRepository;
import bokbookbok.server.domain.book.repository.VoteUserRepository;
import bokbookbok.server.domain.review.dao.ReviewRepository;
import bokbookbok.server.domain.review.domain.Review;
import bokbookbok.server.domain.user.domain.User;
import bokbookbok.server.global.config.common.codes.ErrorCode;
import bokbookbok.server.global.config.exception.BusinessExceptionHandler;
import bokbookbok.server.infra.OpenAIClient;
import bokbookbok.server.infra.util.GptResponseParser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final VoteUserRepository voteUserRepository;
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    private final OpenAIClient openAIClient;

    @Transactional
    public VoteResultResponse vote(Long bookId, User user, VoteOption option) {
        Vote vote = voteRepository.findByBook_Id(bookId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.VOTE_NOT_FOUND));

        boolean alreadyVoted = voteUserRepository.existsByVoteAndUser(vote, user);
        if (alreadyVoted) {
            throw new BusinessExceptionHandler(ErrorCode.ALREADY_VOTED);
        }

        if (option == VoteOption.A) {
            vote.incrementCountA();
        } else {
            vote.incrementCountB();
        }

        voteUserRepository.save(VoteUser.create(vote, user, option));
        return createVoteResultResponse(vote, user);
    }

    @Transactional(readOnly = true)
    public VoteResultResponse getVoteResult(Long bookId, User user) {
        Vote vote = voteRepository.findByBook_Id(bookId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.VOTE_NOT_FOUND));

        return createVoteResultResponse(vote, user);
    }

    private VoteResultResponse createVoteResultResponse(Vote vote, User user) {
        int countA = vote.getCountA();
        int countB = vote.getCountB();
        int total = countA + countB;

        VoteResultItem resultA = VoteResultItem.builder()
                .option(VoteOption.A)
                .text(vote.getOptionA())
                .count(countA)
                .percentage(total == 0 ? 0.0 : (countA * 100.0) / total)
                .build();

        VoteResultItem resultB = VoteResultItem.builder()
                .option(VoteOption.B)
                .text(vote.getOptionB())
                .count(countB)
                .percentage(total == 0 ? 0.0 : (countB * 100.0) / total)
                .build();

        VoteUser voteUser = voteUserRepository.findByVoteAndUser(vote, user).orElse(null);
        VoteOption myVote = voteUser != null ? voteUser.getSelected() : null;

        return VoteResultResponse.builder()
                .voteResult(List.of(resultA, resultB))
                .myVote(myVote)
                .question(vote.getQuestion())
                .build();
    }

    public void checkAndCreateVoteIfNeeded(Long bookId) {
        List<Review> reviews = reviewRepository.findAllByBookId(bookId);

        if (reviews.size() < 5) {
            return;
        }

        if (voteRepository.existsByBookId(bookId)) {
            return;
        }

        String prompt = buildPromptFromReviews(reviews);
        String result = openAIClient.question(prompt);

        GptResponseParser.ParsedVoteQuestion parsed = GptResponseParser.parse(result);

        Vote vote = Vote.builder()
                .book(bookRepository.getById(bookId))
                .question(parsed.question())
                .optionA(parsed.option1())
                .optionB(parsed.option2())
                .build();

        voteRepository.save(vote);
    }

    private String buildPromptFromReviews(List<Review> reviews) {
        StringBuilder sb = new StringBuilder();
        sb.append("다음은 사람들이 책을 읽고 남긴 감상평들임.\n");
        for (Review r : reviews) {
            sb.append("- ").append(r.getContent()).append("\n");
        }
        sb.append("\n위 감상평들을 기반으로 사람들이 함께 생각해볼 수 있는 흥미로운 질문 하나를 만들고, 그에 대해 서로 반대되는 선택지 2개를 생성해주세요.\n");
        sb.append("형식은 아래와 같아야 합니다. 반드시 형식을 지켜주세요.\n\n");
        sb.append("질문: ...\n");
        sb.append("보기1: ...\n");
        sb.append("보기2: ...\n\n");
        sb.append("예시:\n");
        sb.append("질문: 이 책의 주인공이 다른 선택을 했더라면 더 나은 결과를 얻었을까요?\n");
        sb.append("보기1: 그렇다, 주인공의 선택은 미숙했다.\n");
        sb.append("보기2: 아니다, 그 선택이 최선이었다.\n");
        return sb.toString();
    }
}