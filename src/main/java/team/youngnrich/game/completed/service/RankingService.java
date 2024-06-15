package team.youngnrich.game.completed.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.youngnrich.game.completed.dto.response.MoneyRankingListResponseDto;
import team.youngnrich.game.completed.dto.response.MoneyRankingResponseDto;
import team.youngnrich.game.completed.dto.response.TimeRankingListResponseDto;
import team.youngnrich.game.completed.dto.response.TimeRankingResponseDto;
import team.youngnrich.game.completed.repository.CompletedRepository;
import team.youngnrich.game.fastestRecord.domain.FastestRecord;
import team.youngnrich.game.fastestRecord.repository.FastestRecordRepository;
import team.youngnrich.game.highestProfitRecord.domain.HighestProfitRecord;
import team.youngnrich.game.highestProfitRecord.repository.HighestProfitRecordRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {
    private final FastestRecordRepository fastestRecordRepository;
    private final HighestProfitRecordRepository highestProfitRecordRepository;
    public TimeRankingListResponseDto timeRanking(Authentication authentication) {
        List<FastestRecord> recordList = findAllFastestOrderByTime();
        List<TimeRankingResponseDto> rankedList = new ArrayList<>();
        TimeRankingResponseDto myRankData = null;
        // 동점을 고려하여 순위 부여
        rankedList.add(TimeRankingResponseDto.builder().rank(1L).data(recordList.get(0)).build());
        Long rank = 1L;
        for(int i=1; i<recordList.size(); i++) {
            rank++;
            FastestRecord thisRecord = recordList.get(i);
            if(recordList.get(i-1).getSeconds().equals(thisRecord.getSeconds()))
                rank--;
            rankedList.add(TimeRankingResponseDto.builder().rank(rank).data(thisRecord).build());
            // 랭킹 최상단에 표시할 내 랭킹데이터 찾기
            if(thisRecord.getKakaoId().equals(authentication.getName()))
                myRankData = TimeRankingResponseDto.builder().rank(rank).data(thisRecord).build();
        }
        return TimeRankingListResponseDto.builder().myData(myRankData).records(rankedList).build();
    }

    public MoneyRankingListResponseDto moneyRanking(Authentication authentication) {
        List<HighestProfitRecord> recordList = findAllHighestOrderByMoneyDesc();
        List<MoneyRankingResponseDto> rankedList = new ArrayList<>();
        MoneyRankingResponseDto myRankData = null;
        // 동점을 고려해고 순위 부여
        rankedList.add(MoneyRankingResponseDto.builder().rank(1L).data(recordList.get(0)).build());
        Long rank = 1L;
        for(int i=1; i<recordList.size(); i++) {
            rank++;
            HighestProfitRecord thisRecord = recordList.get(i);
            if(recordList.get(i-1).getMoney().equals(thisRecord.getMoney()))
                rank--;
            rankedList.add(MoneyRankingResponseDto.builder().rank(rank).data(thisRecord).build());
            // 랭킹 최상단에 표시할 내 랭킹데이터 찾기
            if(thisRecord.getKakaoId().equals(authentication.getName()))
                myRankData = MoneyRankingResponseDto.builder().rank(rank).data(thisRecord).build();
        }
        return MoneyRankingListResponseDto.builder().myData(myRankData).records(rankedList).build();
    }

    @Transactional(readOnly = true)
    private List<FastestRecord> findAllFastestOrderByTime() {
        return fastestRecordRepository.findAllOrderBySeconds();
    }

    @Transactional(readOnly = true)
    private List<HighestProfitRecord> findAllHighestOrderByMoneyDesc() {
        return highestProfitRecordRepository.findAllOrderByMoneyDesc();
    }
}
