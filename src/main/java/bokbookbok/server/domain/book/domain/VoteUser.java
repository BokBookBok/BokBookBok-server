package bokbookbok.server.domain.book.domain;

import bokbookbok.server.domain.book.domain.enums.VoteOption;
import bokbookbok.server.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "vote_users")
public class VoteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id", nullable = false)
    private Vote vote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private VoteOption selected;

    public static VoteUser create(Vote vote, User user, VoteOption selected) {
        VoteUser voteUser = new VoteUser();
        voteUser.vote = vote;
        voteUser.user = user;
        voteUser.selected = selected;
        return voteUser;
    }
}
