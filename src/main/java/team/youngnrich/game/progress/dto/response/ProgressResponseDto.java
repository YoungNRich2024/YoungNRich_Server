package team.youngnrich.game.progress.dto.response;

import lombok.Builder;
import lombok.Getter;
import team.youngnrich.game.progress.domain.Progress;

@Getter
public class ProgressResponseDto {
    private Boolean puzzleOne;
    private Boolean puzzleTwo;
    private Boolean puzzleThree;
    private Boolean puzzleFour;
    private Boolean testComplete;
    private Long seconds;
    private Boolean keyObtained;
    private Boolean keyUsed;
    private Long behaviorId;
    private String behaviorName;

    @Builder
    public ProgressResponseDto (Progress progress) {
        this.puzzleOne = progress.isPuzzleOne();
        this.puzzleTwo = progress.isPuzzleTwo();
        this.puzzleThree = progress.isPuzzleThree();
        this.puzzleFour = progress.isPuzzleFour();
        this.testComplete = progress.isTestComplete();
        this.seconds = progress.getSeconds();
        this.keyObtained = progress.isKeyObtained();
        this.keyUsed = progress.isKeyUsed();
        this.behaviorId = null;
        this.behaviorName = null;
        if(progress.isTestComplete()) {
            this.behaviorId = progress.getBehavior().getBehaviorId();
            this.behaviorName = progress.getBehavior().getBehaviorName();
        }
    }
}