package bokbookbok.server.domain.record.application;

import bokbookbok.server.domain.book.domain.Book;
import bokbookbok.server.domain.book.domain.enums.Status;
import bokbookbok.server.domain.book.dto.response.BookInfoResponse;
import bokbookbok.server.domain.record.dao.UserBookRecordRepository;
import bokbookbok.server.domain.record.domain.UserBookRecord;
import bokbookbok.server.domain.record.dto.response.RecordHomeResponse;
import bokbookbok.server.domain.record.dto.response.RecordSummaryResponse;
import bokbookbok.server.domain.user.domain.User;
import bokbookbok.server.global.config.S3.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class UserBookRecordService {

    private final UserBookRecordRepository userBookRecordRepository;
    private final S3Service s3Service;

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public RecordHomeResponse getUserRecords(User user) {
        List<UserBookRecord> reviewedRecords = userBookRecordRepository.findAllByUserAndStatus(user, Status.REVIEWED);

        List<RecordSummaryResponse> summaries = reviewedRecords.stream()
                .map(record ->
                {
                    Book book = record.getBook();
                    String imageUrl = s3Service.getPublicUrl(book.getImageUrl());
                    return RecordSummaryResponse.builder()
                            .bookInfoResponse(BookInfoResponse.from(book, imageUrl))
                            .readDays(record.getReadingDays())
                            .startedAt(record.getStartedAt())
                            .endedAt(record.getEndedAt())
                            .weekLabel(getWeekLabel(record.getStartedAt()))
                            .build();
                })
                .toList();

        return RecordHomeResponse.builder()
                .nickname(user.getNickname())
                .totalCount(summaries.size())
                .records(summaries)
                .build();
    }

    private String getWeekLabel(LocalDate startedAt) {
        int weekOfMonth = startedAt.get(WeekFields.of(Locale.KOREA).weekOfMonth());
        int month = startedAt.getMonthValue();
        return String.format("%d월 %d째주", month, weekOfMonth);
    }
}
