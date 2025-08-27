package bokbookbok.server.domain.book.repository;

import bokbookbok.server.domain.book.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByBook_Id(Long bookId);

    boolean existsByBookId(Long bookId);
}
