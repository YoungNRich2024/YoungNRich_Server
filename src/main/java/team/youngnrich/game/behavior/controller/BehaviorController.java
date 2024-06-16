package team.youngnrich.game.behavior.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.youngnrich.game.behavior.service.BehaviorService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/behavior")
public class BehaviorController {
    private final BehaviorService behaviorService;
    @PostMapping
    public ResponseEntity<String> insertData () {
        return ResponseEntity.ok().body(behaviorService.insertData());
    }
}
