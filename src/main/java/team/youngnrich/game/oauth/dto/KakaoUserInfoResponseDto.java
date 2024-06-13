package team.youngnrich.game.oauth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class KakaoUserInfoResponseDto {
    private String kakaoId;
    private String nickname;
    private String profileImageUrl;

    @Builder
    public KakaoUserInfoResponseDto (String kakaoId, String nickname, String profileImageUrl) {
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}
