package bokbookbok.server.domain.record.dao;

import bokbookbok.server.domain.book.domain.Book;
import bokbookbok.server.domain.record.domain.UserBookRecord;
import bokbookbok.server.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserBookRecordRepository extends JpaRepository<UserBookRecord, Long> {
    Optional<UserBookRecord> findByUserAndBook(User user, Book book);

    Optional<Object> findByBookAndUser(Book book, User user);

    List<UserBookRecord> findAllByBook(Book book);
}
