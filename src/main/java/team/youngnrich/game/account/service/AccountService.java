package team.youngnrich.game.account.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.youngnrich.game.account.domain.Account;
import team.youngnrich.game.account.domain.RefreshToken;
import team.youngnrich.game.account.dto.request.SignupRequestDto;
import team.youngnrich.game.account.dto.response.SignupResponseDto;
import team.youngnrich.game.account.repository.AccountRepository;
import team.youngnrich.game.oauth.dto.KakaoUserInfoResponseDto;
import team.youngnrich.game.oauth.service.KakaoLoginService;
import team.youngnrich.game.utils.JwtUtil;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final KakaoLoginService kakaoLoginService;
    private final RefreshTokenService refreshTokenService;

    // Access 토큰 만료 시간을 1시간으로 설정
    private Long AccessExpireTimeMs = 1000 * 60 * 60L;
    // Refresh 토큰 만료 시간을 7일로 설정
    private Long RefreshExpireTimeMs = 7 * 24 * 1000 * 60 * 60L;

    private static String ACCESS_KEY;
    private static String REFRESH_KEY;

    @Component
    public static class AccountServicePropertyInitializer {
        @Autowired
        public AccountServicePropertyInitializer (Environment environment) {
            ACCESS_KEY = environment.getProperty("spring.jwt.secret-key");
            REFRESH_KEY = environment.getProperty("spring.jwt.refresh-key");
        }
    }

    public SignupResponseDto signup(SignupRequestDto requestDto){
        KakaoUserInfoResponseDto userInfo;
        try {
            userInfo = kakaoLoginService.login(requestDto.getCode());
        } catch (ParseException e) {
            throw new RuntimeException("[ERROR] 카카오 측의 응답 parsing 중 ParseException 발생!");
        }

        if(!accountRepository.existsByKakaoId(userInfo.getKakaoId()))
            accountRepository.save(Account.builder()
                    .kakaoId(userInfo.getKakaoId())
                    .nickname(userInfo.getNickname())
                    .profileImageUrl(userInfo.getProfileImageUrl())
                    .build());

        Account foundAccount = findAccountByKakaoId(userInfo.getKakaoId());

        // 로그인 성공 -> 토큰 생성
        String accessToken = JwtUtil.createAccessToken(foundAccount.getKakaoId(), ACCESS_KEY, AccessExpireTimeMs);
        String refreshToken = JwtUtil.createRefreshToken(foundAccount.getKakaoId(), REFRESH_KEY, RefreshExpireTimeMs);

        // RefreshToken을 DB에 저장
        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setValue(refreshToken);
        refreshTokenEntity.setAccountId(foundAccount.getAccountId());
        refreshTokenService.addRefreshToken(refreshTokenEntity);

        return SignupResponseDto.builder()
                .account(foundAccount)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public SignupResponseDto requestRefresh(String refreshToken) {
        // 해당 RefreshToken이 유효한지 DB에서 탐색
        RefreshToken foundRefreshToken = refreshTokenService.findRefreshToken(refreshToken);
        // RefreshToken에 들어있는 username 값 가져오기
        Claims claims = JwtUtil.parseRefreshToken(foundRefreshToken.getValue(), REFRESH_KEY);
        String kakaoId = claims.get("kakaoId").toString();
        System.out.println("KakaoId found in RefreshToken: " + kakaoId);
        // 가져온 username에 해당하는 회원이 존재하는지 확인
        Account foundAccount = findAccountByKakaoId(kakaoId);

        // 새 AccessKey 생성
        String accessToken = JwtUtil.createAccessToken(foundAccount.getKakaoId(), ACCESS_KEY, AccessExpireTimeMs);

        // 새 AccessKey와 기존 RefreshKey를 DTO에 담아 리턴
        return SignupResponseDto.builder()
                .account(foundAccount)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional(readOnly = true)
    private Account findAccountByKakaoId(String kakaoId) {
        return accountRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new EntityNotFoundException("[ERROR] 존재하지 않는 카카오ID입니다!"));
    }

}
