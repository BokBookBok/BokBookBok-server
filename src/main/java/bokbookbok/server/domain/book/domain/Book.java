package bokbookbok.server.domain.book.domain;

import bokbookbok.server.global.config.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "books")
public class Book extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String description;

    @Column(name = "book_image_url", length = 512)
    private String imageUrl;

    @Column(nullable = false)
    private boolean isCurrent;

    private LocalDate startDate;
    private LocalDate endDate;
}