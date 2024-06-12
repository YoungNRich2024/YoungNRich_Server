package team.youngnrich.game.account.dto.response;

import lombok.Builder;
import lombok.Getter;
import team.youngnrich.game.account.domain.Account;

@Getter
public class SignupResponseDto {
    private Long id;
    private String kakaoId;
    private String nickname;
    private String profileImageUrl;
    private String accessToken;
    private String refreshToken;

    @Builder
    public SignupResponseDto (Account account, String accessToken, String refreshToken) {
        this.id = account.getId();
        this.kakaoId = account.getKakaoId();
        this.nickname = account.getNickname();
        this.profileImageUrl = account.getProfileImageUrl();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
