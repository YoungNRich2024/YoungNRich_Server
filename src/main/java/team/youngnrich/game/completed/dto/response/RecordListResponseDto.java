package team.youngnrich.game.completed.dto.response;

import lombok.*;
import java.util.List;

@Getter
public class RecordListResponseDto {
    private List<RecordResponseDto> records;

    @Builder
    public RecordListResponseDto (List<RecordResponseDto> list) {
        this.records = list;
    }
}
