package team.youngnrich.game.account.service;

import org.assertj.core.api.SoftAssertions;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import team.youngnrich.game.account.domain.Account;
import team.youngnrich.game.account.dto.request.SignupRequestDto;
import team.youngnrich.game.account.dto.response.SignupResponseDto;
import team.youngnrich.game.account.repository.AccountRepository;
import team.youngnrich.game.oauth.dto.KakaoUserInfoResponseDto;
import team.youngnrich.game.oauth.service.KakaoLoginService;

import javax.persistence.EntityNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestPropertySource(properties = {
        "spring.config.location = src/main/resources/application.yml"
})
class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;
    @Mock
    KakaoLoginService kakaoLoginService;
    @Mock
    RefreshTokenService refreshTokenService;

    @Test
    void signup_테스트_오류확인_클라이언트가_잘못된_인가코드를_전달한_경우() throws ParseException {
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
        KakaoUserInfoResponseDto infoResponseDto = KakaoUserInfoResponseDto.builder()
                .kakaoId("12345678")
                .nickname("nickname")
                .profileImageUrl("example.com")
                .build();
        when(kakaoLoginService.login(any(String.class))).thenReturn(infoResponseDto);
        AccountService accountService = new AccountService(accountRepository, kakaoLoginService, refreshTokenService);
        // when, then
        assertThrows(EntityNotFoundException.class, () -> accountService.signup(REQUEST_DTO));
    }

}