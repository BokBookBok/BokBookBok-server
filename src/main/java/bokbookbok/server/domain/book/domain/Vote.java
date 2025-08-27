package bokbookbok.server.domain.book.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "votes")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne
    private Book book;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String optionA;
    @Column(nullable = false)
    private String optionB;

    private int countA;
    private int countB;

    public void incrementCountA() {
        this.countA++;
    }

    public void incrementCountB() {
        this.countB++;
    }
}
