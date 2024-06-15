package team.youngnrich.game.completed.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClearRequestDto {
    private Long seconds;
    private Long money;
}
