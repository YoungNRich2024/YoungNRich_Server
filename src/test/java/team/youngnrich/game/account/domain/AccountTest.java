package team.youngnrich.game.account.domain;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.config.location = src/main/resources/application.yml"
})
class AccountTest {
    @Value("${numbers.account.nickname-max}")
    private Long NICKNAME_MAX_LENGTH;

    @Test
    void 새회원생성_성공테스트() {
        // given
        String KAKAO_ID = "12345678";
        String NICKNAME = "nickname";
        String PROFILE_IMAGE_URL = "example.com";
        // when
        Account account = Account.builder()
                .kakaoId(KAKAO_ID)
                .nickname(NICKNAME)
                .profileImageUrl(PROFILE_IMAGE_URL)
                .build();
        // then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(account.getKakaoId()).isEqualTo(KAKAO_ID);
        softly.assertThat(account.getNickname()).isEqualTo(NICKNAME);
        softly.assertThat(account.getProfileImageUrl()).isEqualTo(PROFILE_IMAGE_URL);
        softly.assertAll();
    }

    @Test
    void 새회원생성_실패테스트_닉네임_글자수_초과() {
        // given
        String KAKAO_ID = "12345678";
        String concatedString = "";
        for (int i=0; i<NICKNAME_MAX_LENGTH+1; i++) {
            concatedString = concatedString.concat("a");
        }
        String TOO_LONG_NICKNAME = concatedString;
        String PROFILE_IMAGE_URL = "example.com";
        // when, then
        assertThrows(IllegalArgumentException.class,
                () -> Account.builder().kakaoId(KAKAO_ID).nickname(TOO_LONG_NICKNAME).profileImageUrl(PROFILE_IMAGE_URL).build());
    }

    @Test
    void 닉네임수정_성공테스트() {
        // given
        String OLD_NICKNAME = "old";
        String NEW_NICKNAME = "new";
        Account account = Account.builder()
                .kakaoId("12345678")
                .nickname(OLD_NICKNAME)
                .profileImageUrl("example.com")
                .build();
        // when
        account.updateNickname(NEW_NICKNAME);
        // then
        assertThat(account.getNickname()).isEqualTo(NEW_NICKNAME);
    }

    @Test
    void 회원정보수정_실패테스트_닉네임_글자수_초과() {
        // given
        String OLD_NICKNAME = "old";
        String concatedString = "";
        for (int i=0; i<NICKNAME_MAX_LENGTH+1; i++) {
            concatedString = concatedString.concat("a");
        }
        String NEW_NICKNAME = concatedString;
        Account account = Account.builder()
                .kakaoId("12345678")
                .nickname(OLD_NICKNAME)
                .profileImageUrl("example.com")
                .build();
        // when, then
        assertThrows(IllegalArgumentException.class, () -> account.updateNickname(NEW_NICKNAME));
    }
}