package team.youngnrich.game.progress.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveRequestDto {
    @NotNull
    private Long seconds;

    @Builder
    public SaveRequestDto (Long seconds) {
        this.seconds = seconds;
    }
}
