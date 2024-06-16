package team.youngnrich.game.behavior.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.youngnrich.game.behavior.domain.Behavior;

@Repository
public interface BehaviorRepository extends JpaRepository<Behavior, Long> {
}
