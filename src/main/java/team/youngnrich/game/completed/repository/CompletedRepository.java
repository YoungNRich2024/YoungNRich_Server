package team.youngnrich.game.completed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.youngnrich.game.account.domain.Account;
import team.youngnrich.game.completed.domain.Completed;

import java.util.List;

@Repository
public interface CompletedRepository extends JpaRepository<Completed, Long> {
    List<Completed> findAllByAccountOrderByCompletedId(Account account);
    Boolean existsByAccount(Account account);
}
