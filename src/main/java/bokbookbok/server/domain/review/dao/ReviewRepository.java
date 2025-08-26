package bokbookbok.server.domain.review.dao;

import bokbookbok.server.domain.book.domain.Book;
import bokbookbok.server.domain.review.domain.Review;
import bokbookbok.server.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByUserAndBook(User user, Book book);
    List<Review> findAllByBook(Book book);

}
