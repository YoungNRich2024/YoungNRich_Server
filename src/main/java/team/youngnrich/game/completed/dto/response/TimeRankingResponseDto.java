package team.youngnrich.game.completed.dto.response;

import lombok.Builder;
import lombok.Getter;
import team.youngnrich.game.completed.domain.Completed;
import team.youngnrich.game.fastestRecord.domain.FastestRecord;

@Getter
public class TimeRankingResponseDto {
    private Long rank;
    private String nickname;
    private String profileImageUrl;
    private long seconds;

    @Builder
    public TimeRankingResponseDto (Long rank, FastestRecord data) {
        this.rank = rank;
        this.nickname = data.getAccount().getNickname();
        this.profileImageUrl = data.getAccount().getProfileImageUrl();
        this.seconds = data.getSeconds();
    }
}
