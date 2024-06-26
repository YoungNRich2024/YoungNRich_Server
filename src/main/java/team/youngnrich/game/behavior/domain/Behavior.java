package team.youngnrich.game.behavior.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.youngnrich.game.completed.domain.Completed;
import team.youngnrich.game.progress.domain.Progress;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Behavior {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "behavior_id", nullable = false, updatable = false)
    private Long behaviorId;

    @Column(nullable = false)
    private String behaviorName;

    @OneToMany(mappedBy = "behavior")
    private List<Progress> progressList = new ArrayList<>();

    @OneToMany(mappedBy = "behavior")
    private List<Completed> completedList = new ArrayList<>();

    @Builder
    public Behavior (Long behaviorId, String behaviorName) {
        this.behaviorId = behaviorId;
        this.behaviorName = behaviorName;
    }
}