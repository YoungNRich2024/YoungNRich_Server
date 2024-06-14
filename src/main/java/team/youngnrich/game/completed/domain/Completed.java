package team.youngnrich.game.completed.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import team.youngnrich.game.account.domain.Account;
import team.youngnrich.game.behavior.domain.Behavior;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Completed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "completed_id", nullable = false, updatable = false)
    private Long completedId;

    @Column(nullable = false)
    private LocalDateTime completedTime;

    @Column(nullable = false)
    private Long seconds;

    @Column(nullable = false)
    private Long money;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="behavior_id")
    Behavior behavior;
}