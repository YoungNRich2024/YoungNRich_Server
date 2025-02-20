package team.youngnrich.game.youngCompleted.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MoneyRankingListResponseDto {
    private MoneyRankingResponseDto myData;
    private List<MoneyRankingResponseDto> records;

    @Builder
    public MoneyRankingListResponseDto(MoneyRankingResponseDto myData, List<MoneyRankingResponseDto> records) {
        this.myData = myData;
        this.records = records;
    }
}
