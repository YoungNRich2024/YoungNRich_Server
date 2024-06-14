package team.youngnrich.game.progress.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import team.youngnrich.game.account.domain.Account;
import team.youngnrich.game.behavior.domain.Behavior;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progress_id", nullable = false, updatable = false)
    private Long progressId;

    @Column(nullable = false)
    private boolean puzzleOne;

    @Column(nullable = false)
    private boolean puzzleTwo;

    @Column(nullable = false)
    private boolean puzzleThree;

    @Column(nullable = false)
    private boolean puzzleFour;

    @Column(nullable = false)
    private boolean testComplete;

    @Column(nullable = false)
    private Long seconds;

    @Column(nullable = false)
    private boolean keyObtained;

    @Column(nullable = false)
    private boolean keyUsed;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="behavior_id")
    Behavior behavior;

    @Builder
    public Progress (Account owner) {
        this.account = owner;
        this.puzzleOne = false;
        this.puzzleTwo = false;
        this.puzzleThree = false;
        this.puzzleFour = false;
        this.testComplete = false;
        this.seconds = 0L;
        this.keyObtained = false;
        this.keyUsed = false;
    }

    public Progress init () {
        Progress newProgress = Progress.builder()
                .owner(this.account)
                .build();
        newProgress.setProgressId(this.progressId);
        return newProgress;
    }
}