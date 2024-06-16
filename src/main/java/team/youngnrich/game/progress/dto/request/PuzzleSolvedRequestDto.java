package team.youngnrich.game.progress.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PuzzleSolvedRequestDto {
    @NotNull
    private Long number;
}