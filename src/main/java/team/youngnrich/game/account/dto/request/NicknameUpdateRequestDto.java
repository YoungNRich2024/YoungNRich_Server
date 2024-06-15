package team.youngnrich.game.account.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NicknameUpdateRequestDto {
    @NotNull
    private String nickname;

    @Builder
    public NicknameUpdateRequestDto (String nickname) {
        this.nickname = nickname;
    }
}
