package team.youngnrich.game.account.service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import team.youngnrich.game.account.dto.request.SignupRequestDto;
import team.youngnrich.game.account.dto.response.SignupResponseDto;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    @Test
    void signup_테스트_정상_응답값확인() {
        // given
        Scanner sc = new Scanner(System.in);
        String CODE_PROVIDED_BY_CLIENT = sc.nextLine();
        SignupRequestDto REQUEST_DTO = SignupRequestDto.builder()
                .code(CODE_PROVIDED_BY_CLIENT)
                .build();
        AccountService accountService = new AccountService();
        // when
        SignupResponseDto responseDto = accountService.signup(REQUEST_DTO);
        // then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(responseDto.getId()).isNotNull();
        softly.assertThat(responseDto.getKakaoId()).isNotNull();
        softly.assertThat(responseDto.getNickname()).isNotNull();
        softly.assertThat(responseDto.getProfileImageUrl()).isNotNull();
        softly.assertThat(responseDto.getAccessToken()).isNotNull();
        softly.assertThat(responseDto.getRefreshToken()).isNotNull();
        softly.assertAll();
    }

    @Test
    void signup_테스트_오류확인_클라이언트가_잘못된_인가코드를_전달한_경우() {
        // given
        String WRONG_AUTHORIZATION_CODE = "abcd";
        SignupRequestDto REQUEST_DTO = SignupRequestDto.builder()
                .code(WRONG_AUTHORIZATION_CODE)
                .build();
        AccountService accountService = new AccountService();
        // when, then
        assertThrows(IllegalArgumentException.class, () -> accountService.signup(REQUEST_DTO));
    }

}