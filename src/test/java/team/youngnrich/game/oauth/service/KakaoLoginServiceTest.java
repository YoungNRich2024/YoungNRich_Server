package team.youngnrich.game.oauth.service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertThrows;

class KakaoLoginServiceTest {
    @Test
    void login_테스트_인가코드으로_카카오엑세스토큰받고_유저정보까지() {
        // given
        Scanner sc = new Scanner(System.in);
        String CODE_PROVIDED_BY_CLIENT = sc.nextLine();
        KakaoLoginService kakaoLoginService = new KakaoLoginService();
        // when
        KakaoUserInfoResponseDto infoDto = kakaoLoginService.login(CODE_PROVIDED_BY_CLIENT);
        // then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(infoDto.getKakaoId()).isNotNull();
        softly.assertThat(infoDto.getNickname()).isNotNull();
        softly.assertThat(infoDto.getProfileImageUrl()).isNotNull();
        softly.assertAll();
    }

    @Test
    void 카카오요청_오류_캐치_테스트() {
        // given
        String WRONG_AUTHORIZATION_CODE = "abcd";
        KakaoLoginService kakaoLoginService= new KakaoLoginService();
        // when, then
        assertThrows(IllegalArgumentException.class, () -> kakaoLoginService.login(WRONG_AUTHORIZATION_CODE));
    }

}