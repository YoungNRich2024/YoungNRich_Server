package team.youngnrich.game.oauth.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class KakaoLoginServiceTest {
    @Test
    void 카카오요청_오류_캐치_테스트() {
        // given
        String WRONG_AUTHORIZATION_CODE = "abcd";
        KakaoLoginService kakaoLoginService= new KakaoLoginService();
        // when, then
        assertThrows(IllegalArgumentException.class, () -> kakaoLoginService.login(WRONG_AUTHORIZATION_CODE));
    }

}