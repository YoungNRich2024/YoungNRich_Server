package team.youngnrich.game.completed.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.youngnrich.game.completed.dto.response.MoneyRankingListResponseDto;
import team.youngnrich.game.completed.dto.response.TimeRankingListResponseDto;
import team.youngnrich.game.completed.service.RankingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking")
public class RankingController {
    private final RankingService rankingService;

    @GetMapping("/time")
    public TimeRankingListResponseDto timeRanking(Authentication authentication) {
        return rankingService.timeRanking(authentication);
    }

    @GetMapping("/money")
    public MoneyRankingListResponseDto moneyRanking(Authentication authentication) {
        return rankingService.moneyRanking(authentication);
    }
}
