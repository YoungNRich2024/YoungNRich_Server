package team.youngnrich.game.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.youngnrich.game.account.dto.request.RefreshTokenRequestDto;
import team.youngnrich.game.account.dto.request.SignupRequestDto;
import team.youngnrich.game.account.dto.response.SignupResponseDto;
import team.youngnrich.game.account.service.AccountService;
import team.youngnrich.game.account.service.RefreshTokenService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public SignupResponseDto signup (@RequestBody SignupRequestDto requestDto) {
        return accountService.signup(requestDto);
    }

    @DeleteMapping("/logout")
    public String logout(@RequestBody RefreshTokenRequestDto requestDto) {
        refreshTokenService.deleteRefreshToken(requestDto.getRefreshToken());
        return "성공적으로 로그아웃되었습니다!";
    }

    @PostMapping("/refreshtoken")
    public SignupResponseDto requestRefresh (@RequestBody RefreshTokenRequestDto refreshTokenDto) {
        return accountService.requestRefresh(refreshTokenDto.getRefreshToken());
    }
}
