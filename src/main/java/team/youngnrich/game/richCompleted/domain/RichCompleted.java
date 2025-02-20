package team.youngnrich.game.richCompleted.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import team.youngnrich.game.account.domain.Account;
import team.youngnrich.game.richHighest.domain.RichHighest;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RichCompleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "r_completed_id", nullable = false, updatable = false)
    private Long rCompletedId;

    @Column(name = "r_completed_time", nullable = false)
    private LocalDateTime rCompletedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Account account;

    @Column(nullable = false)
    private Double money;

    @Column(name= "earnings_rate", nullable = false)
    private Double earningsRate;

    @OneToOne(mappedBy = "richCompleted")
    RichHighest richHighest;

    @Builder
    public RichCompleted (Account account, Double money, Double earningsRate) {
        this.rCompletedTime = LocalDateTime.now();
        this.account = account;
        this.money = money;
        this.earningsRate = earningsRate;
    }

}
