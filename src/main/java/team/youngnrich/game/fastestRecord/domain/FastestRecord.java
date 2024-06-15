package team.youngnrich.game.fastestRecord.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import team.youngnrich.game.account.domain.Account;
import team.youngnrich.game.completed.domain.Completed;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FastestRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fastest_record_id", nullable = false, updatable = false)
    private Long fastestRecordId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Account account;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "completed_id", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Completed completed;

    @Builder
    public FastestRecord (Account account, Completed completed) {
        this.account = account;
        this.completed = completed;
    }
}
