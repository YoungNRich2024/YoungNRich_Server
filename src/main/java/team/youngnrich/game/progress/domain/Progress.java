package team.youngnrich.game.progress.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import team.youngnrich.game.account.domain.Account;
import team.youngnrich.game.behavior.domain.Behavior;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progress_id", nullable = false, updatable = false)
    private Long progressId;

    @Column(nullable = false)
    private Boolean puzzleOne;

    @Column(nullable = false)
    private Boolean puzzleTwo;

    @Column(nullable = false)
    private Boolean puzzleThree;

    @Column(nullable = false)
    private Boolean puzzleFour;

    @Column(nullable = false)
    private Boolean testComplete;

    @Column(nullable = false)
    private Long seconds;

    @Column(nullable = false)
    private Boolean keyObtained;

    @Column(nullable = false)
    private Boolean keyUsed;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="behavior_id")
    Behavior behavior;
}