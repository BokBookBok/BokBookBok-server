package bokbookbok.server.domain.record.application;

import bokbookbok.server.domain.book.domain.Book;
import bokbookbok.server.domain.book.domain.enums.Status;
import bokbookbok.server.domain.record.dao.UserBookRecordRepository;
import bokbookbok.server.domain.record.domain.UserBookRecord;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserBookService {

    private final UserBookRecordRepository userBookRecordRepository;

    public int getAverageReadingDaysByBook(Book book) {
        List<UserBookRecord> records = userBookRecordRepository.findAllByBook(book);

        List<Integer> readingDays = records.stream()
                .filter(r -> r.getStatus() != Status.NOT_STARTED)
                .map(UserBookRecord::getReadingDays)
                .toList();

        if (readingDays.isEmpty()) return 0;

        int total = readingDays.stream().mapToInt(Integer::intValue).sum();
        return total / readingDays.size();
    }
}
