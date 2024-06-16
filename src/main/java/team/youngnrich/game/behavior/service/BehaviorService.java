package team.youngnrich.game.behavior.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.youngnrich.game.behavior.domain.Behavior;
import team.youngnrich.game.behavior.repository.BehaviorRepository;

@Service
@RequiredArgsConstructor
public class BehaviorService {
    private final BehaviorRepository behaviorRepository;

    public String insertData () {
        behaviorRepository.save(Behavior.builder().behaviorId(1L).behaviorName("안정형").build());
        behaviorRepository.save(Behavior.builder().behaviorId(2L).behaviorName("안정추구형").build());
        behaviorRepository.save(Behavior.builder().behaviorId(3L).behaviorName("위험중립형").build());
        behaviorRepository.save(Behavior.builder().behaviorId(4L).behaviorName("적극투자형").build());
        behaviorRepository.save(Behavior.builder().behaviorId(5L).behaviorName("공격투자형").build());
        return "Success";
    }
}
