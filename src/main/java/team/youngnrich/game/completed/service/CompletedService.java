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
import team.youngnrich.game.fastestRecord.domain.FastestRecord;
import team.youngnrich.game.fastestRecord.repository.FastestRecordRepository;
import team.youngnrich.game.highestProfitRecord.domain.HighestProfitRecord;
import team.youngnrich.game.highestProfitRecord.repository.HighestProfitRecordRepository;
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
    private final FastestRecordRepository fastestRecordRepository;
    private final HighestProfitRecordRepository highestProfitRecordRepository;

    public String clear(Authentication authentication, ClearRequestDto requestDto) throws AuthenticationException {
        Account account = accountRepository.findByKakaoId(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("[ERROR] 존재하지 않는 카카오ID입니다!"));
        Progress foundProgress = findProgressByAccount(account);
        validateOwner(authentication.getName(), foundProgress);
        Completed completed = Completed.builder()
                .seconds(requestDto.getSeconds())
                .money(requestDto.getMoney())
                .account(foundProgress.getAccount())
                .behavior(foundProgress.getBehavior())
                .build();
        // 중간저장 기록 삭제
        progressRepository.delete(foundProgress);
        // 완료기록 생성
        Completed savedCompleted = completedRepository.save(completed);
        // 최고기록 갱신
        if(existsFastestByAccount(account)) {
            // 시간 기준 최고기록 갱신
            FastestRecord fastestRecord = findFastestByAccount(account);
            if(fastestRecord.getCompleted().getSeconds() > savedCompleted.getSeconds()){
                fastestRecordRepository.delete(fastestRecord);
                fastestRecordRepository.save(FastestRecord.builder()
                        .account(account)
                        .completed(savedCompleted)
                        .build());
            }
            // 수익률 기준 최고기록 갱신
            HighestProfitRecord highestProfitRecord = findHighestByAccount(account);
            if(highestProfitRecord.getCompleted().getMoney() < savedCompleted.getMoney()) {
                highestProfitRecordRepository.delete(highestProfitRecord);
                highestProfitRecordRepository.save(HighestProfitRecord.builder()
                        .account(account)
                        .completed(savedCompleted)
                        .build());
            }
            return "Success";
        }
        fastestRecordRepository.save(FastestRecord.builder()
                .account(account)
                .completed(savedCompleted)
                .build());
        highestProfitRecordRepository.save(HighestProfitRecord.builder()
                .account(account)
                .completed(savedCompleted)
                .build());
        return "Success";
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
    private Progress findProgressByAccount(Account account)  {
        return progressRepository.findByAccount(account)
                .orElseThrow(() -> new EntityNotFoundException("[ERROR] 해당 회원의 중간저장 데이터가 없습니다!"));
    }

    @Transactional(readOnly = true)
    private List<Completed> findAllByKakaoId(String kakaoId) {
        Account account = accountRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new EntityNotFoundException("[ERROR] 존재하지 않는 카카오ID입니다!"));
        return completedRepository.findAllByAccountOrderByCompletedId(account);
    }

    @Transactional(readOnly = true)
    private boolean existsCompletedByAccount(Account account) {
        return completedRepository.existsByAccount(account);
    }
    @Transactional(readOnly = true)
    private FastestRecord findFastestByAccount(Account account) {
        return fastestRecordRepository.findByAccount(account)
                .orElseThrow(() -> new EntityNotFoundException("[ERROR] 최단기록이 존재하지 않습니다!"));
    }

    @Transactional(readOnly = true)
    private HighestProfitRecord findHighestByAccount(Account account) {
        return highestProfitRecordRepository.findByAccount(account)
                .orElseThrow(() -> new EntityNotFoundException("[ERROR] 최고기록이 존재하지 않습니다!"));
    }

    @Transactional(readOnly = true)
    private boolean existsFastestByAccount (Account account) {
        return fastestRecordRepository.existsByAccount(account);
    }
}
