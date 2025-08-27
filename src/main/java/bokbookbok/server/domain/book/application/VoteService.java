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

        VoteUser voteUser = VoteUser.create(vote, user, option);
        voteUserRepository.save(voteUser);

        int total = vote.getCountA() + vote.getCountB();
        VoteResultItem resultA = VoteResultItem.builder()
                .option(VoteOption.A)
                .text(vote.getOptionA())
                .count(vote.getCountA())
                .percentage((vote.getCountA() * 100.0) / total)
                .build();

        VoteResultItem resultB = VoteResultItem.builder()
                .option(VoteOption.B)
                .text(vote.getOptionB())
                .count(vote.getCountB())
                .percentage((vote.getCountB() * 100.0) / total)
                .build();

        return VoteResultResponse.builder()
                .voteResult(List.of(resultA, resultB))
                .build();
    }
}

