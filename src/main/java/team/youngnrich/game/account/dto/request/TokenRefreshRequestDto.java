package team.youngnrich.game.account.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenRefreshRequestDto {
    private String refreshToken;
}