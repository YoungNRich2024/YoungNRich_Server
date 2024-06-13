package team.youngnrich.game.account.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class RefreshToken {
    // 고유 키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshTokenId;

    // 이 토큰의 주인인 회원의 고유 키
    @Column(nullable = false)
    private Long accountId;

    // 이 토큰의 값
    @Column(nullable = false)
    private String value;
}