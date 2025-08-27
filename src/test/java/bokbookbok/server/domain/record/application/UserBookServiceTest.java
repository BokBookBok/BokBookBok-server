package bokbookbok.server.domain.record.application;

import bokbookbok.server.domain.book.domain.Book;
import bokbookbok.server.domain.book.domain.enums.Status;
import bokbookbok.server.domain.record.dao.UserBookRecordRepository;
import bokbookbok.server.domain.record.domain.UserBookRecord;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class UserBookServiceTest {
    @Mock
    private UserBookRecordRepository recordRepository;

    @InjectMocks
    private UserBookService userBookService;

    public UserBookServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 평균_독서일수를_올바르게_계산한다() {
        // given
        Book book = Book.builder().id(1L).build();

        UserBookRecord record1 = UserBookRecord.builder()
                .status(Status.READING)
                .startedAt(LocalDate.now().minusDays(3))
                .build();

        UserBookRecord record2 = UserBookRecord.builder()
                .status(Status.READ_COMPLETED)
                .startedAt(LocalDate.now().minusDays(7))
                .endedAt(LocalDate.now().minusDays(2))
                .build();

        UserBookRecord record3 = UserBookRecord.builder()
                .status(Status.NOT_STARTED)
                .build();

        when(recordRepository.findAllByBook(book)).thenReturn(List.of(record1, record2, record3));


        int result = userBookService.getAverageReadingDaysByBook(book);

        // then
        // record1: 4일 (3일 전 ~ 오늘 → +1 포함)
        // record2: 6일 (7일 전 ~ 2일 전 → 5 + 1)
        int expected = (4 + 6) / 2;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void 독서기록이_없는_경우_평균_0을_반환한다() {
        Book book = Book.builder().id(2L).build();
        when(recordRepository.findAllByBook(book)).thenReturn(List.of());

        int result = userBookService.getAverageReadingDaysByBook(book);

        assertThat(result).isEqualTo(0);
    }

    @Test
    void 모두_NOT_STARTED이면_평균_0을_반환한다() {
        Book book = Book.builder().id(3L).build();

        UserBookRecord record1 = UserBookRecord.builder()
                .status(Status.NOT_STARTED)
                .build();
        UserBookRecord record2 = UserBookRecord.builder()
                .status(Status.NOT_STARTED)
                .build();

        when(recordRepository.findAllByBook(book)).thenReturn(List.of(record1, record2));

        int result = userBookService.getAverageReadingDaysByBook(book);

        assertThat(result).isEqualTo(0);
    }
}