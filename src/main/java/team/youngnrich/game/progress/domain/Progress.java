package team.youngnrich.game.progress.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import team.youngnrich.game.account.domain.Account;

import javax.persistence.*;

@Entity
@Getter
@Setter // 게임 진행에 따라 중간저장 내역이 업데이트될 때마다 Setter로 각 필드 수정
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
    private boolean keyObtained;

    @Column(nullable = false)
    private boolean keyUsed;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Account account;

    // 진행률 제로인 새 중간저장 데이터를 만드는 빌더
    @Builder
    public Progress (Account owner) {
        this.account = owner;
        this.puzzleOne = false;
        this.puzzleTwo = false;
        this.puzzleThree = false;
        this.puzzleFour = false;
        this.keyObtained = false;
        this.keyUsed = false;
    }

    // 중간저장 기록이 존재하는 유저가 새 게임을 시작했을 때 중간저장을 초기화하는 함수
    public Progress init () {
        Progress newProgress = Progress.builder()
                .owner(this.account)
                .build();
        newProgress.setProgressId(this.progressId);
        return newProgress;
    }
}