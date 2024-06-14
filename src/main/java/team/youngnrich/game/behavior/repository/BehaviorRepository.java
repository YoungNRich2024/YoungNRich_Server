package team.youngnrich.game.behavior.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.youngnrich.game.behavior.domain.Behavior;

public interface BehaviorRepository extends JpaRepository<Behavior, Long> {
}
