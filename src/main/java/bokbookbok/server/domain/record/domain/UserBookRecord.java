package bokbookbok.server.domain.record.domain;

import bokbookbok.server.domain.book.domain.Book;
import bokbookbok.server.domain.book.domain.enums.Status;
import bokbookbok.server.domain.user.domain.User;
import bokbookbok.server.global.config.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "record")
public class UserBookRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.NOT_STARTED;

    private LocalDate startedAt;
    private LocalDate endedAt;

    public void updateStatusWithDate(Status newStatus, LocalDate today) {
        this.status = newStatus;

        if (newStatus == Status.READING && this.startedAt == null) {
            this.startedAt = today;
        }

        if (newStatus == Status.READ_COMPLETED) {
            this.endedAt = today;
        }
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
}
