package bokbookbok.server.domain.book.repository;

import bokbookbok.server.domain.book.domain.Vote;
import bokbookbok.server.domain.book.domain.VoteUser;
import bokbookbok.server.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteUserRepository extends JpaRepository<VoteUser, Long> {
    boolean existsByVoteAndUser(Vote vote, User user);
}
