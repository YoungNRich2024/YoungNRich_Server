package team.youngnrich.game.account.dto.response;

import lombok.Builder;
import lombok.Getter;
import team.youngnrich.game.account.domain.Account;

@Getter
public class MyDataResponseDto {
    private String nickname;
    private String profileImageUrl;

    @Builder
    public MyDataResponseDto(Account account) {
        this.nickname = account.getNickname();
        this.profileImageUrl = account.getProfileImageUrl();
    }
}
