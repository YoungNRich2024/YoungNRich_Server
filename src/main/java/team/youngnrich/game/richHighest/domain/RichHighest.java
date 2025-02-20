package team.youngnrich.game.richHighest.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import team.youngnrich.game.account.domain.Account;
import team.youngnrich.game.richCompleted.domain.RichCompleted;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RichHighest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "r_highest_id", nullable = false, updatable = false)
    private Long rHighestId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Account account;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "r_completed_id", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RichCompleted richCompleted;

    @Column(nullable = false)
    private Double money;

    @Column(nullable = false)
    private Double earningsRate;

    @Builder
    public RichHighest (Account account, RichCompleted richCompleted) {
        this.account = account;
        this.richCompleted = richCompleted;
        this.money = richCompleted.getMoney();
        this.earningsRate = richCompleted.getEarningsRate();
    }

}
