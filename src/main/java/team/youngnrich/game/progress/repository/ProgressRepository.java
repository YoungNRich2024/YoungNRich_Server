package team.youngnrich.game.progress.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.youngnrich.game.account.domain.Account;
import team.youngnrich.game.progress.domain.Progress;

import java.util.Optional;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {
    Optional<Progress> findByAccount(Account account);
    Boolean existsByAccount(Account account);
}