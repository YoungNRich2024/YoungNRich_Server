package team.youngnrich.game.youngCompleted.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class TimeRankingListResponseDto {
    private TimeRankingResponseDto myData;
    private List<TimeRankingResponseDto> records;

    @Builder
    public TimeRankingListResponseDto (TimeRankingResponseDto myData, List<TimeRankingResponseDto> records) {
        this.myData = myData;
        this.records = records;
    }
}
