package bokbookbok.server.domain.book.repository;

import bokbookbok.server.domain.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}