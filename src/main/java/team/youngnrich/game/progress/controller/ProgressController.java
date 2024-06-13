package team.youngnrich.game.progress.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import team.youngnrich.game.progress.service.ProgressService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/progress")
public class ProgressController {
    private final ProgressService progressService;

    @GetMapping("/exist")
    public ProgressExistResponseDto checkIfExists (Authentication authentication) {
        return progressService.checkIfExists(authentication.getName());
    }

    @GetMapping
    public ProgressResponseDto getMyProgress (Authentication authentication) {
        return progressService.getMyProgress(authentication.getName());
    }

    @PostMapping("/puzzle")
    public ResponseEntity<String> puzzleSolved (Authentication authentication, PuzzleSolvedRequestDto requestDto) {
        return progressService.puzzleSolved(authentication.getName(), requestDto);
    }

    @PostMapping("/keyget")
    public ResponseEntity<String> getKey (Authentication authentication) {
        return progressService.getKey(authentication.getName());
    }

    @PostMapping("/keyuse")
    public ResponseEntity<String> useKey (Authentication authentication) {
        return progressService.useKey(authentication.getName());
    }

    @PostMapping("/test")
    public ResponseEntity<String> test (Authentication authentication, @RequestBody TestRequestDto requestDto) {
        return progressService.test(authentication.getName(), requestDto);
    }

    @PostMapping("/new")
    public ResponseEntity<String> newGame (Authentication authentication) {
        return progressService.newGame(authentication.getName());
    }

    @PostMapping("/save")
    public ResponseEntity<String> save (Authentication authentication, SaveRequestDto requestDto) {
        return progressService.save(authentication.getName(), requestDto);
    }
}
