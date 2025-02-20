package team.youngnrich.game.youngCompleted.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.youngnrich.game.account.domain.Account;
import team.youngnrich.game.youngCompleted.domain.YoungCompleted;

import java.util.List;

@Repository
public interface CompletedRepository extends JpaRepository<YoungCompleted, Long> {
    List<YoungCompleted> findAllByAccountOrderByCompletedId(Account account);
    Boolean existsByAccount(Account account);
}
