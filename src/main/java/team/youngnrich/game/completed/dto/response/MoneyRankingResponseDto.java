package team.youngnrich.game.completed.dto.response;

import lombok.Builder;
import lombok.Getter;
import team.youngnrich.game.highestProfitRecord.domain.HighestProfitRecord;

@Getter
public class MoneyRankingResponseDto {
    private Long rank;
    private String nickname;
    private String profileImageUrl;
    private Long money;
    private Double earningsRate;

    @Builder
    public MoneyRankingResponseDto(Long rank, HighestProfitRecord data) {
        this.rank = rank;
        this.nickname = data.getAccount().getNickname();
        this.profileImageUrl = data.getAccount().getProfileImageUrl();
        this.money = data.getMoney();
        this.earningsRate = data.getEarningsRate();
    }
}
