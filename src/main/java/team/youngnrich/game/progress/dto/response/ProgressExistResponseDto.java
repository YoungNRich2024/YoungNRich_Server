package team.youngnrich.game.progress.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProgressExistResponseDto {
    private Boolean exists;

    @Builder
    public  ProgressExistResponseDto (Boolean exists) {
        this.exists = exists;
    }
}
