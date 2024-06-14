package team.youngnrich.game.progress.domain;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class ProgressTest {
    @Test
    void 새게임시작_시_초기화_테스트() {
        Progress ORIGINAL_PROGRESS = Progress.builder()
                .owner(null)
                .build();
        // when
        Progress NEW_PROGRESS = ORIGINAL_PROGRESS.init();
        // then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(NEW_PROGRESS.isPuzzleOne()).isFalse();
        softly.assertThat(NEW_PROGRESS.isPuzzleTwo()).isFalse();
        softly.assertThat(NEW_PROGRESS.isPuzzleThree()).isFalse();
        softly.assertThat(NEW_PROGRESS.isPuzzleFour()).isFalse();
        softly.assertThat(NEW_PROGRESS.isKeyObtained()).isFalse();
        softly.assertThat(NEW_PROGRESS.isKeyUsed()).isFalse();
        softly.assertThat(NEW_PROGRESS.getSeconds()).isEqualTo(0L);
        softly.assertThat(NEW_PROGRESS.getBehavior()).isNull();
        softly.assertAll();
    }
}