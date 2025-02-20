package team.youngnrich.game.youngCompleted.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import team.youngnrich.game.account.domain.Account;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class YoungCompleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "y_completed_id", nullable = false, updatable = false)
    private Long yCompletedId;

    @Column(name = "y_completed_time", nullable = false)
    private LocalDateTime yCompletedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Account account;

    @Builder
    public YoungCompleted(Account account) {
        this.yCompletedTime = LocalDateTime.now();
        this.account = account;
    }
}