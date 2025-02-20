package team.youngnrich.game.youngCompleted.dto.response;

import lombok.Builder;
import lombok.Getter;
import team.youngnrich.game.youngCompleted.domain.YoungCompleted;

import java.time.LocalDateTime;

@Getter
public class RecordResponseDto {
    private LocalDateTime completedTime;
    private Long seconds;
    private Long money;
    private Double earningsRate;
    private String behaviorName;

    @Builder
    public RecordResponseDto(YoungCompleted completed) {
        this.completedTime = completed.getCompletedTime();
        this.seconds = completed.getSeconds();
        this.money = completed.getMoney();
        this.earningsRate = this.money / 100000000.0 * 100;
        this.behaviorName = completed.getBehavior().getBehaviorName();
    }
}