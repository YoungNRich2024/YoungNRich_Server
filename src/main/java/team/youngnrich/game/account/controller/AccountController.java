package team.youngnrich.game.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import team.youngnrich.game.account.dto.request.NicknameUpdateRequestDto;
import team.youngnrich.game.account.dto.request.RefreshTokenRequestDto;
import team.youngnrich.game.account.dto.request.SignupRequestDto;
import team.youngnrich.game.account.dto.response.MyDataResponseDto;
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
    public ResponseEntity<String> logout(@RequestBody RefreshTokenRequestDto requestDto) {
        refreshTokenService.deleteRefreshToken(requestDto.getRefreshToken());
        return ResponseEntity.ok().body("Success");
    }

    @PostMapping("/nickname")
    public ResponseEntity<String> updateNickname(Authentication authentication, @RequestBody NicknameUpdateRequestDto requestDto) {
        accountService.updateNickname(authentication.getName(), requestDto);
        return ResponseEntity.ok().body("Success");
    }

    @GetMapping("/mydata")
    public MyDataResponseDto getMyData(Authentication authentication) {
        return accountService.getMyData(authentication.getName());
    }


    @PostMapping("/refreshtoken")
    public SignupResponseDto requestRefresh (@RequestBody RefreshTokenRequestDto refreshTokenDto) {
        return accountService.requestRefresh(refreshTokenDto.getRefreshToken());
    }
}
