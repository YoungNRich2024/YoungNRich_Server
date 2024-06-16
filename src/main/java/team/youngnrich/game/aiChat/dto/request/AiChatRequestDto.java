package team.youngnrich.game.aiChat.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AiChatRequestDto {
    @NotNull
    private String keyword;

    @Builder
    public AiChatRequestDto (String keyword) {
        this.keyword = keyword;
    }
}
