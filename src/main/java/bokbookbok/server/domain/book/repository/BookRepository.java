package bokbookbok.server.domain.book.repository;

import bokbookbok.server.domain.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Modifying
    @Query("UPDATE Book b SET b.isCurrent = false WHERE b.isCurrent = true")
    void resetAllCurrentFlags();

    @Query("SELECT b FROM Book b WHERE b.isCurrent = true")
    Optional<Book> findCurrentBook();

}