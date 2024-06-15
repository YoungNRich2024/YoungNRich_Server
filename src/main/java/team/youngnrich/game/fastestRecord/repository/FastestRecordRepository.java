package team.youngnrich.game.fastestRecord.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.youngnrich.game.account.domain.Account;
import team.youngnrich.game.fastestRecord.domain.FastestRecord;

import java.util.Optional;

public interface FastestRecordRepository extends JpaRepository<FastestRecord, Long> {
    Optional<FastestRecord> findByAccount(Account account);
    Boolean existsByAccount(Account account);
}
