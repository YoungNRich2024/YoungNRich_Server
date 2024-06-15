package team.youngnrich.game.completed.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.youngnrich.game.account.domain.Account;
import team.youngnrich.game.account.repository.AccountRepository;
import team.youngnrich.game.completed.domain.Completed;
import team.youngnrich.game.completed.dto.request.ClearRequestDto;
import team.youngnrich.game.completed.dto.response.RecordListResponseDto;
import team.youngnrich.game.completed.dto.response.RecordResponseDto;
import team.youngnrich.game.completed.repository.CompletedRepository;
import team.youngnrich.game.progress.domain.Progress;
import team.youngnrich.game.progress.repository.ProgressRepository;

import javax.naming.AuthenticationException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompletedService {
    private final CompletedRepository completedRepository;
    private final ProgressRepository progressRepository;
    private final AccountRepository accountRepository;

    public String clear(Authentication authentication, ClearRequestDto requestDto) throws AuthenticationException {
        Progress foundProgress = findProgressByKakaoId(authentication.getName());
        validateOwner(authentication.getName(), foundProgress);
        Completed completed = Completed.builder()
                .seconds(requestDto.getSeconds())
                .money(requestDto.getMoney())
                .account(foundProgress.getAccount())
                .behavior(foundProgress.getBehavior())
                .build();
        progressRepository.delete(foundProgress);
        completedRepository.save(completed);
        return null;
    }

    public RecordListResponseDto getMyRecord(Authentication authentication) {
        List<Completed> recordList= findAllByKakaoId(authentication.getName());
        List<RecordResponseDto> dtoList = new ArrayList<>();
        for(Completed c : recordList)
            dtoList.add(RecordResponseDto.builder().completed(c).build());
        return RecordListResponseDto.builder().list(dtoList).build();
    }

    @Transactional(readOnly = true)
    private void validateOwner (String kakaoId, Progress progress) throws AuthenticationException {
        Account loggedInUser = accountRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new EntityNotFoundException("[ERROR] 존재하지 않는 카카오ID입니다!"));
        Long loggedInUserId = loggedInUser.getAccountId();
        Long progressOwnersId = progress.getAccount().getAccountId();

        if(loggedInUserId.equals(progressOwnersId))
            return;
        throw new AuthenticationException("[ERROR] 로그인한 유저와 중간저장 데이터를 소유한 유저가 다릅니다!");
    }

    @Transactional(readOnly = true)
    private Progress findProgressByKakaoId(String kakaoId)  {
        Account account = accountRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new EntityNotFoundException("[ERROR] 존재하지 않는 카카오ID입니다!"));
        return progressRepository.findByAccount(account)
                .orElseThrow(() -> new EntityNotFoundException("[ERROR] 해당 회원의 중간저장 데이터가 없습니다!"));
    }

    @Transactional(readOnly = true)
    private List<Completed> findAllByKakaoId(String kakaoId) {
        Account account = accountRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new EntityNotFoundException("[ERROR] 존재하지 않는 카카오ID입니다!"));
        return completedRepository.findAllByAccountOrderByCompletedId(account);
    }
}
