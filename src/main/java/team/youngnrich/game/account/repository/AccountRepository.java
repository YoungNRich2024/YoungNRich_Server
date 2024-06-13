package team.youngnrich.game.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.youngnrich.game.account.domain.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Boolean existsByKakaoId(String kakaoId);

    Optional<Account> findByKakaoId(String kakaoId);
}
