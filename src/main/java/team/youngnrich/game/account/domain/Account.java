package team.youngnrich.game.account.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import team.youngnrich.game.completed.domain.Completed;
import team.youngnrich.game.fastestRecord.domain.FastestRecord;
import team.youngnrich.game.highestProfitRecord.domain.HighestProfitRecord;
import team.youngnrich.game.progress.domain.Progress;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Transient
    private static Long NICKNAME_MAX_LENGTH;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false, updatable = false)
    private Long accountId;

    @Column(nullable = false)
    private String kakaoId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String profileImageUrl;

    @OneToOne(mappedBy = "account")
    Progress progress;

    @OneToMany(mappedBy = "account")
    List<Completed> completedList = new ArrayList<>();

    @OneToOne(mappedBy = "account")
    FastestRecord fastestRecord;

    @OneToOne(mappedBy = "account")
    HighestProfitRecord highestProfitRecord;

    @Builder
    public Account(String kakaoId, String nickname, String profileImageUrl) {
        validateNickname(nickname);
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public void updateNickname(String newNickname) {
        validateNickname(newNickname);
        this.nickname = newNickname;
    }

    public void updateProfileImageUrl(String newUrl) {
        this.profileImageUrl = newUrl;
    }

    private void validateNickname(String nickname) {
        if(nickname.length()> NICKNAME_MAX_LENGTH)
            throw new IllegalArgumentException("[ERROR] 닉네임이 너무 깁니다!: " + nickname);
    }

    @Component
    public static class AccountPropertyInitializer {
        @Autowired
        public AccountPropertyInitializer(Environment environment) {
            NICKNAME_MAX_LENGTH = Long.parseLong(environment.getProperty("numbers.account.nickname-max"));
        }
    }
}