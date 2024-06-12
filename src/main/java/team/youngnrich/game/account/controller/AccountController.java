package team.youngnrich.game.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.youngnrich.game.account.dto.request.SignupRequestDto;
import team.youngnrich.game.account.dto.response.SignupResponseDto;
import team.youngnrich.game.account.service.AccountService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/signup")
    public SignupResponseDto signup (@RequestBody SignupRequestDto requestDto) {
        return accountService.signup(requestDto);
    }

}
