package team.youngnrich.game.progress.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.youngnrich.game.account.domain.Account;
import team.youngnrich.game.progress.domain.Progress;

import java.util.Optional;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
    Optional<Progress> findByAccount(Account account);
}