package team.youngnrich.game.progress.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.youngnrich.game.account.domain.Account;
import team.youngnrich.game.account.repository.AccountRepository;
import team.youngnrich.game.behavior.domain.Behavior;
import team.youngnrich.game.behavior.repository.BehaviorRepository;
import team.youngnrich.game.progress.domain.Progress;
import team.youngnrich.game.progress.dto.request.PuzzleSolvedRequestDto;
import team.youngnrich.game.progress.dto.request.SaveRequestDto;
import team.youngnrich.game.progress.dto.request.TestRequestDto;
import team.youngnrich.game.progress.dto.response.ProgressExistResponseDto;
import team.youngnrich.game.progress.dto.response.ProgressResponseDto;
import team.youngnrich.game.progress.repository.ProgressRepository;

@Service
@RequiredArgsConstructor
public class ProgressService {
    private final ProgressRepository progressRepository;
    private final AccountRepository accountRepository;
    private final BehaviorRepository behaviorRepository;

    private final String NO_ACCOUNT_ERROR_MESSAGE = "[ERROR] 해당 카카오ID를 가진 회원이 없습니다!";
    private final String NO_PROGRESS_ERROR_MESSAGE = "[ERROR] 해당 회원의 중간저장 데이터가 없습니다!";

    public ProgressExistResponseDto checkIfExists(String kakaoId) {
        boolean result = true;
        try {
            findProgressByKakaoId(kakaoId);
        } catch (IllegalArgumentException e) {
            if(e.getMessage().contentEquals(NO_PROGRESS_ERROR_MESSAGE))
                result = false;
            if(e.getMessage().contentEquals(NO_ACCOUNT_ERROR_MESSAGE))
                throw e;
        }
        return ProgressExistResponseDto.builder().exists(result).build();
    }

    public ProgressResponseDto getMyProgress(String kakaoId) {
        return ProgressResponseDto.builder()
                .progress(findProgressByKakaoId(kakaoId))
                .build();
    }

    public String puzzleSolved(String kakaoId, PuzzleSolvedRequestDto requestDto) {
        Progress foundProgress = findProgressByKakaoId(kakaoId);
        switch (requestDto.getPuzzleNumber()) {
            case 1: foundProgress.setPuzzleOne(true);
                break;
            case 2: foundProgress.setPuzzleTwo(true);
                break;
            case 3: foundProgress.setPuzzleThree(true);
                break;
            case 4: foundProgress.setPuzzleFour(true);
                break;
        }
        progressRepository.save(foundProgress);
        return "Success";
    }

    public String getKey(String kakaoId) {
        Progress foundProgress = findProgressByKakaoId(kakaoId);
        foundProgress.setKeyObtained(true);
        progressRepository.save(foundProgress);
        return "Success";
    }

    public String useKey(String kakaoId) {
        Progress foundProgress = findProgressByKakaoId(kakaoId);
        foundProgress.setKeyUsed(true);
        progressRepository.save(foundProgress);
        return "Success";
    }

    public String test(String kakaoId, TestRequestDto requestDto) {
        Progress foundProgress = findProgressByKakaoId(kakaoId);
        foundProgress.setTestComplete(true);
        foundProgress.setBehavior(findBehaviorById(requestDto.getResult()));
        progressRepository.save(foundProgress);
        return "Success";
    }

    public String newGame(String kakaoId) {
        Progress foundProgress = findProgressByKakaoId(kakaoId);
        progressRepository.save(foundProgress.init());
        return "Success";
    }

    public String save(String kakaoId, SaveRequestDto requestDto) {
        Progress foundProgress = findProgressByKakaoId(kakaoId);
        foundProgress.setSeconds(requestDto.getSeconds());
        progressRepository.save(foundProgress);
        return "Success";
    }

    @Transactional(readOnly = true)
    private Progress findProgressByKakaoId(String kakaoId) {
        Account owner = accountRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new IllegalArgumentException(NO_ACCOUNT_ERROR_MESSAGE));
        return progressRepository.findByAccount(owner)
                .orElseThrow(() -> new IllegalArgumentException(NO_PROGRESS_ERROR_MESSAGE));
    }

    @Transactional(readOnly = true)
    private Behavior findBehaviorById(Long id) {
        return behaviorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 투자성향의 ID는 1~5 사이의 정수여야 합니다!"));
    }
}