package bokbookbok.server.domain.book.application;

import bokbookbok.server.domain.book.domain.Vote;
import bokbookbok.server.domain.book.domain.VoteUser;
import bokbookbok.server.domain.book.domain.enums.VoteOption;
import bokbookbok.server.domain.book.dto.response.VoteResultItem;
import bokbookbok.server.domain.book.dto.response.VoteResultResponse;
import bokbookbok.server.domain.book.repository.VoteRepository;
import bokbookbok.server.domain.book.repository.VoteUserRepository;
import bokbookbok.server.domain.user.domain.User;
import bokbookbok.server.global.config.common.codes.ErrorCode;
import bokbookbok.server.global.config.exception.BusinessExceptionHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final VoteUserRepository voteUserRepository;

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
}