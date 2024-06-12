package team.youngnrich.game.account.service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.youngnrich.game.account.domain.Account;
import team.youngnrich.game.account.dto.request.SignupRequestDto;
import team.youngnrich.game.account.dto.response.SignupResponseDto;
import team.youngnrich.game.account.repository.AccountRepository;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    @Test
    void signup_테스트_정상_응답값확인() {
        // given
        Scanner sc = new Scanner(System.in);
        String CODE_PROVIDED_BY_CLIENT = sc.nextLine();
        SignupRequestDto REQUEST_DTO = SignupRequestDto.builder()
                .code(CODE_PROVIDED_BY_CLIENT)
                .build();
        Account account = Account.builder()
                .kakaoId("12345678")
                .nickname("nickname")
                .profileImageUrl("example.com")
                .build();
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        AccountService accountService = new AccountService(accountRepository);
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
        Account account = Account.builder()
                .kakaoId("12345678")
                .nickname("nickname")
                .profileImageUrl("example.com")
                .build();
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        AccountService accountService = new AccountService(accountRepository);
        // when, then
        assertThrows(IllegalArgumentException.class, () -> accountService.signup(REQUEST_DTO));
    }

}