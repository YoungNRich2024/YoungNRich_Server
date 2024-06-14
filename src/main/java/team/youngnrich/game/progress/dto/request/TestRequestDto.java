package team.youngnrich.game.progress.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TestRequestDto {
    @NotNull
    private Long result;

    @Builder
    public TestRequestDto (Long result) {
        this.result = result;
    }
}