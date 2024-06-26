package team.youngnrich.game.highestProfitRecord.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.youngnrich.game.account.domain.Account;
import team.youngnrich.game.highestProfitRecord.domain.HighestProfitRecord;

import java.util.List;
import java.util.Optional;

@Repository
public interface HighestProfitRecordRepository extends JpaRepository<HighestProfitRecord, Long> {
    Optional<HighestProfitRecord> findByAccount(Account account);
    Boolean existsByAccount(Account account);
    List<HighestProfitRecord> findAllByOrderByMoneyDesc();
}
